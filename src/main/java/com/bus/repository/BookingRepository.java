package com.bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bus.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
}

