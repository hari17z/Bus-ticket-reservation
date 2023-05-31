package com.bus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bus.service.BusService;
import com.bus.model.Bus;

@Controller
@RequestMapping("/admin")
public class AdminBusController {

    @Autowired
    private BusService busService;

    @GetMapping("/buses")
    public String showBuses(Model model) {
        
    	List<Bus> buses = busService.getAllBuses();

        model.addAttribute("buses", buses);
        return "admin/buses";
    }


    @GetMapping("/buses/add")
    public String showAddBusForm(Model model) {
        Bus bus = new Bus();
        model.addAttribute("bus", bus);
        return "admin/add-bus";
    }

    @PostMapping("/buses/save")
    public String saveOrUpdateBus(@ModelAttribute("bus") Bus bus) {
        busService.saveOrUpdateBus(bus);
        return "redirect:/admin/buses";
    }

    @GetMapping("/buses/edit/{id}")
    public String showEditBusForm(@PathVariable("id") Long id, Model model) {
        Bus bus = busService.getBusById(id);
        model.addAttribute("bus", bus);
        return "admin/edit-bus";
    }

    @GetMapping("/buses/delete/{id}")
    public String deleteBus(@PathVariable("id") Long id) {
        busService.deleteBus(id);
        return "redirect:/admin/buses";
    }

}

