package com.bus.service;

import com.bus.model.Booking;

public interface BookingService {
	
    Booking saveBooking(Booking booking, long user_id, long busId, Integer numTickets, double price) throws Exception;
    Booking getBookingById(Long bookingId);



}
