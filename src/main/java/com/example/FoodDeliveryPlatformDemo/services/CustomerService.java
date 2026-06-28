package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.patch.CustomerPatchDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerAddressRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.request.CustomerRequestDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerAddressResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.CustomerResponseDTO;
import com.example.FoodDeliveryPlatformDemo.dto.response.OrderResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Customer;
import com.example.FoodDeliveryPlatformDemo.entities.CustomerAddress;
import com.example.FoodDeliveryPlatformDemo.exceptions.*;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerAddressRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.CustomerRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService{

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    CustomerRepository customerRepository;
    CustomerAddressRepository customerAddressRepository;


    public CustomerResponseDTO patchCustomer(Integer id, CustomerPatchDTO dto) {

        Customer customer = customerRepository.getById(id);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }

        if (dto.getPhone() != null) {
            if(dto.getPhone().equalsIgnoreCase(customer.getPhone())){
                throw new InvalidRequestException("New Value can not be as previous value");
            }
            customer.setPhone(dto.getPhone());
        }

        if (dto.getEmail() != null) {
            if (customerRepository.existsByEmail(dto.getEmail())) {
                throw new InvalidRequestException("Email already exists");
            }
            if(dto.getEmail().equalsIgnoreCase(customer.getEmail())){
                throw new InvalidRequestException("New Value can not be as previous value");
            }
            customer.setEmail(dto.getEmail());
        }

        if (dto.getCustomerAddress() != null) {
            customer.getCustomerAddresses().add(CustomerAddressRequestDTO.toEntity(dto.getCustomerAddress()));
        }

        if (dto.getFirstName() != null) {
            if(dto.getFirstName().equalsIgnoreCase(customer.getFirstName())){
                throw new InvalidRequestException("New Value can not be as previous value");
            }
            customer.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            if(dto.getLastName().equalsIgnoreCase(customer.getLastName())){
                throw new InvalidRequestException("New Value can not be as previous value");
            }
            customer.setLastName(dto.getLastName());
        }

        return CustomerResponseDTO.toResponse(customerRepository.save(customer)) ;
    }

    public Page<Customer> search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customerRepository.search(name, pageable);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        if(HelperUtils.isNull(dto)){
            throw new NullRequestBodyException("Customer request cannot be null.");
        }
        Customer customer = CustomerRequestDTO.toEntity(dto);
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new InvalidRequestException("Email already exists");
        }
        customer.setIsActive(true);
        customer.setCustomerCode(HelperUtils.generateId("CUST-",4));
        customer.setCreatedDate(new Date());
        customer.setLoyaltyPoints(0);
        customerRepository.save(customer);

        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress) {
        if(HelperUtils.isNull(dto)){
            throw new NullRequestBodyException("Customer request cannot be null.");
        }
        if(HelperUtils.isNull(initialAddress)){
            throw new NullRequestBodyException("Customer address request cannot be null.");
        }

        Customer customer = CustomerRequestDTO.toEntity(dto);
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new InvalidRequestException("Email already exists");
        }
        CustomerAddress address = CustomerAddressRequestDTO.toEntity(initialAddress);
        address.setIsActive(true);
        customer.getCustomerAddresses().add(address);
        customer.setCustomerCode(HelperUtils.generateId("CUST-",4));
        customer.setCreatedDate(new Date());
        customer.setLoyaltyPoints(0);
        customerAddressRepository.save(address);

        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address){

        if(HelperUtils.isNull(address)){
            throw new NullRequestBodyException("address request body is null");
        }

        Customer customer = customerRepository.getById(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        CustomerAddress customerAddress = CustomerAddressRequestDTO.toEntity(address);
        customerAddress.setIsActive(true);
        customerAddress.setCreatedDate(new Date());
        customerAddress.setCustomer(customer);
        if(HelperUtils.isNull(customer.getCustomerAddresses())){
            customerAddress.setIsDefault(true);
        }else {
            customerAddress.setIsDefault(false);

        }
        customer.getCustomerAddresses().add(customerAddress);
        customer.setUpdatedDate(new Date());
        customerAddress.setCustomer(customer);
        customerAddress.setIsActive(true);
        customerAddressRepository.save(customerAddress);
        customerRepository.save(customer);

        return CustomerResponseDTO.toResponse(customer);
    }



    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points){
        Customer customer = customerRepository.getById(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        if (HelperUtils.isNegative(points)) {
            throw new InvalidLoyaltyPointsException("Points must be positive");
        }


        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        customer.setUpdatedDate(new Date());
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO applyLoyaltyPenalty(Integer customerId, int pointsDeducted) {
        Customer customer = customerRepository.getById(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }

        if (pointsDeducted < 0) {
            throw new InvalidLoyaltyPointsException("Points must be positive");
        }

        if (pointsDeducted > customer.getLoyaltyPoints()) {
            throw new InvalidLoyaltyPointsException("Not enough points");
        }
        customer.setLoyaltyPoints(HelperUtils.subtract(customer.getLoyaltyPoints(), pointsDeducted));
        customer.setUpdatedDate(new Date());
        customerRepository.save(customer);
        return CustomerResponseDTO.toResponse(customer);
    }

    public String deactivateCustomer(Integer customerId){
        Customer customer = customerRepository.getById(customerId);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        customer.setIsActive(false);
        customerRepository.save(customer);
        return "Customer deactivated successfully";
    }

    public List<CustomerResponseDTO> getAllCustomers(){
        return CustomerResponseDTO.toResponse(customerRepository.getAll());
    }
    public CustomerResponseDTO getById(Integer id){
        if(HelperUtils.isNull(id)){
            throw new InvalidRequestException("Id is null");
        }
        Customer customer = customerRepository.getById(id);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        return CustomerResponseDTO.toResponse(customer);
    }

    public CustomerResponseDTO getByEmail(String email){
        if(HelperUtils.isNull(email)){
            throw new InvalidRequestException("email is null");
        }
        Customer customer = customerRepository.findByEmail(email);
        if(HelperUtils.isNull(customer)){
            throw new CustomerNotFoundException();
        }
        return CustomerResponseDTO.toResponse(customer);
    }

    public List<CustomerAddressResponseDTO> getAllAddressesByCustomer(Integer customerId) {
        if(HelperUtils.isNull(customerId)){
            throw new InvalidRequestException("Id is null");
        }
        return CustomerAddressResponseDTO.toResponse(customerRepository.findAddressesByCustomerId(customerId));
    }

    public CustomerAddressResponseDTO setDefault(Integer addressId){
        CustomerAddress customerAddress = customerAddressRepository.getById(addressId);
        CustomerAddress defaultAddress = customerAddressRepository.getDefaultAddress(customerAddress.getCustomer().getId());
        if(HelperUtils.isNotNull(defaultAddress)){
        defaultAddress.setIsDefault(false);
            customerAddressRepository.save(defaultAddress);}

        if(HelperUtils.isNull(customerAddress)){
            throw new AddressNotFoundException();
        }
        customerAddress.setIsDefault(true);
        customerAddress.setUpdatedDate(new Date());
        customerAddressRepository.save(customerAddress);
        return CustomerAddressResponseDTO.toResponse(customerAddress);
    }

    public String deleteAddress(Integer addressId){
        CustomerAddress customerAddress = customerAddressRepository.getById(addressId);
        if(HelperUtils.isNull(customerAddress)){
            throw new AddressNotFoundException();
        }
        customerAddress.setIsActive(false);
        customerAddress.setUpdatedDate(new Date());

        if(customerAddress.getIsDefault()){
            customerAddress.setIsDefault(false);
            customerAddressRepository.save(customerAddress);
            CustomerAddress defaultAddress = customerAddressRepository.findFirst(customerAddress.getCustomer().getId());
            defaultAddress.setIsDefault(true);
            defaultAddress.setUpdatedDate(new Date());
            customerAddressRepository.save(defaultAddress);
        }

        customerAddress.setIsActive(false);
        customerAddress.setUpdatedDate(new Date());
        customerAddressRepository.save(customerAddress);
        return "Address deleted successfully";
    }

    public List<OrderResponseDTO> getAllOrdersByCustomer(Integer customerId) {
        if(HelperUtils.isNull(customerId)){
            throw new InvalidRequestException("Id is null");
        }
        return OrderResponseDTO.toResponse(customerRepository.findOrdersByCustomerId(customerId));
    }

    public List<CustomerResponseDTO> getTopLoyalCustomers() {

        List<Customer> customers = customerRepository
                .findTop10ByOrderByLoyaltyPointsDesc();

        return CustomerResponseDTO.toResponse(customers);
    }





}
