package com.bus.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bus.model.Booking;
import com.bus.model.Bus;
import com.bus.model.User;
import com.bus.repository.BookingRepository;
import com.bus.service.BookingService;
import com.bus.service.BusService;
import com.bus.service.UserService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserService userservice;
    
    @Autowired
    private BusService busservice;
    @Override
    public Booking saveBooking(Booking booking, long user_id, long busId, Integer numTickets, double price) throws Exception {
        Bus bus = busservice.getBusById(busId);
        User user = userservice.getUserById(user_id);
        
        if (bus.getAvailableSeats() <= 0 || bus.getAvailableSeats() - numTickets < 0) {
            throw new Exception("All seats are booked.");
        }
        
        bus.setAvailableSeats(bus.getAvailableSeats() - numTickets);
        Bus updated = busservice.saveOrUpdateBus(bus);
        
        booking.setBus(busservice.getBusById(busId));
        booking.setUser(user);
        booking.setDate(bus.getDate());
        booking.setPrice(price);
        booking.setNumOfTickets(numTickets);
        
        System.out.println("saved successfully");
        
        bookingRepository.save(booking);
        
        return booking;
    }
    

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }


}

