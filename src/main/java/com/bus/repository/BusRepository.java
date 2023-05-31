package com.bus.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bus.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
	
    List<Bus> findBySourceAndDestinationAndDate(String source, String destination, LocalDate date);
}