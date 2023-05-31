package com.bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.bus.model.Payment;
import com.bus.model.User;
import com.bus.service.BookingService;
import com.bus.service.PaymentService;
import com.bus.service.UserService;

import java.security.Principal;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserController userController;

    @PostMapping("/process-payment")
    public String processPayment(@ModelAttribute Payment payment, @RequestParam Long bookid,
                                 @RequestParam Double amount, Model model, Principal principal) {
        System.out.println("Payment started");

        // Set the booking ID in the payment object
        payment.setBookingId(bookid);
        payment.setAmountPaid(amount);
        // Save the payment
        Payment savedPayment = paymentService.savePayment(payment);
        System.out.println("Payment saved");

        model.addAttribute("payment", savedPayment);

        // Fetch the logged-in user
        User user = userController.getLoggedUser(principal);

        // Send an email to the user
        sendBookingConfirmationEmail(savedPayment, user);

        return "payment-success";
    }

    private void sendBookingConfirmationEmail(Payment payment, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); // Set the recipient email address

        // Set the email subject and body
        message.setSubject("Ticket Booking Confirmation");
        message.setText("Dear Customer,\n\nYour ticket has been booked successfully. " +
                "Payment ID: " + payment.getPaymentId() + "\n\nThank you for choosing our service.");

        // Send the email
        mailSender.send(message);
    }
}
