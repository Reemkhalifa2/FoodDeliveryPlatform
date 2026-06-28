package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.response.PaymentResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import com.example.FoodDeliveryPlatformDemo.enums.OrderStatus;
import com.example.FoodDeliveryPlatformDemo.exceptions.InvalidPaymentStatusException;
import com.example.FoodDeliveryPlatformDemo.exceptions.ObjectNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.OrderNotFoundException;
import com.example.FoodDeliveryPlatformDemo.repositories.OrderRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.PaymentRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    public PaymentService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }
    PaymentRepository paymentRepository;
    OrderRepository orderRepository;

    public PaymentResponseDTO  processPayment(Integer orderId, String method) {
        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        Payment payment = new Payment();
        payment.setIsActive(true);
        payment.setCreatedDate(new Date());
        payment.setPaymentMethod(method);
        payment.setTransactionRef(HelperUtils.generateId("p-"));
        payment.setStatus("PENDING");
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        paymentRepository.save(payment);
        order.setPayment(payment);
        orderRepository.save(order);
        return PaymentResponseDTO.toResponse(payment);

    }

    public PaymentResponseDTO refundPayment(Integer orderId) {
        Order order = orderRepository.getById(orderId);
        if (HelperUtils.isNull(order)) throw new OrderNotFoundException();

        Payment payment = order.getPayment();
        if (HelperUtils.isNull(payment)) throw new ObjectNotFoundException("Payment not found");

        if (payment.getStatus() == null || !payment.getStatus().equalsIgnoreCase("COMPLETE")) {
            throw new InvalidPaymentStatusException("Only paid payments can be refunded.");
        }

        payment.setStatus("REFUNDED");
        payment.setUpdatedDate(new Date());
        paymentRepository.save(payment);
        return PaymentResponseDTO.toResponse(payment);
    }

    public PaymentResponseDTO markPaymentAsSuccessful(Integer paymentId){
        Payment payment =  paymentRepository.getById(paymentId);
        if(HelperUtils.isNull(payment)){
            throw new ObjectNotFoundException("Payment not found");
        }
        payment.setStatus("COMPLETE");
        payment.setUpdatedDate(new Date());
        paymentRepository.save(payment);
        return PaymentResponseDTO.toResponse(payment);
    }
    public Page<Payment> getPayments(String paymentMethod, String status,
                                 String from, String to,
                                 int page, int size) {

        Date fromDate = HelperUtils.startOfDay(HelperUtils.parse(from));
        Date toDate   = HelperUtils.endOfDay(HelperUtils.parse(to));
        if (!HelperUtils.isValidRange(fromDate, toDate)) {
            throw new IllegalArgumentException("'from' date must be before 'to' date");
        }

        Pageable pageable = PageRequest.of(page, size);

        return paymentRepository.filterPayments(paymentMethod ,status, fromDate, toDate, pageable) ;
    }

    public Map<String, Double> getPaymentAnalyticsByMethod() {

        List<Object[]> results = paymentRepository.getTotalAmountGroupedByMethod();

        Map<String, Double> response = new HashMap<>();

        for (Object[] row : results) {

            String method = (String) row[0];
            Double totalAmount = (Double) row[1];

            response.put(method, totalAmount);
        }

        return response;
    }


}

