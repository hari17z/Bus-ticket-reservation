package com.bus.service;

import java.time.LocalDate;
import java.util.List;

import com.bus.model.Bus;

public interface BusService {
    
    List<Bus> getAllBuses();
    
    List<Bus> findBusesByRouteAndDate(String source, String destination, LocalDate date);
    
    Bus getBusById(Long id);
    
    Bus saveOrUpdateBus(Bus bus);
    
    void deleteBus(Long id);

    
}

