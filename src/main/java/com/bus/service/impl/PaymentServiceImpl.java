package com.bus.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.model.Booking;
import com.bus.model.Payment;
import com.bus.repository.PaymentRepository;
import com.bus.service.BookingService;
import com.bus.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private BookingService bookingService;
	
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
    	System.out.println("generating the id");
        String paymentId = generatePaymentId();
    	System.out.println("setting the id");

        payment.setPaymentId(paymentId);
    	System.out.println("saving the id");

        return paymentRepository.save(payment);
    }

    private String generatePaymentId() {
        return "PAY" + UUID.randomUUID().toString().substring(0, 8);
    }
}
