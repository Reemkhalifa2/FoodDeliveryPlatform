package com.example.FoodDeliveryPlatformDemo.services;

import com.example.FoodDeliveryPlatformDemo.dto.response.PaymentResponseDTO;
import com.example.FoodDeliveryPlatformDemo.entities.Order;
import com.example.FoodDeliveryPlatformDemo.entities.Payment;
import com.example.FoodDeliveryPlatformDemo.exceptions.InvalidPaymentStatusException;
import com.example.FoodDeliveryPlatformDemo.exceptions.ObjectNotFoundException;
import com.example.FoodDeliveryPlatformDemo.exceptions.OrderNotFoundException;
import com.example.FoodDeliveryPlatformDemo.repositories.OrderRepository;
import com.example.FoodDeliveryPlatformDemo.repositories.PaymentRepository;
import com.example.FoodDeliveryPlatformDemo.utilities.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Date;

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
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        order.setPayment(payment);
        orderRepository.save(order);
        paymentRepository.save(payment);
        return PaymentResponseDTO.toResponse(payment);

    }

    public PaymentResponseDTO refundPayment(Integer orderId) {

        Order order = orderRepository.getById(orderId);
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }

        Payment payment = order.getPayment();
        if(HelperUtils.isNull(order)){
            throw new OrderNotFoundException();
        }
        if (!payment.getStatus().equalsIgnoreCase("PAID")) {
            throw new InvalidPaymentStatusException(
                    "Only paid payments can be refunded.");
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



}

