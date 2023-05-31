package com.bus.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.model.Bus;
import com.bus.repository.BusRepository;
import com.bus.service.BusService;


@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;
    

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    @Override
    public Bus saveOrUpdateBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
    
    @Override
    public List<Bus> findBusesByRouteAndDate(String source, String destination, LocalDate date) {
    	List<Bus> AvailableBuses = busRepository.findBySourceAndDestinationAndDate(source, destination, date)
                .stream()
                .filter(bus -> bus.getAvailableSeats() > 0)
                .collect(Collectors.toList());
        return AvailableBuses;
    }
}
