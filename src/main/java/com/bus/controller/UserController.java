package com.bus.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bus.model.Booking;
import com.bus.model.Bus;
import com.bus.model.User;
import com.bus.service.BookingService;
import com.bus.service.BusService;
import com.bus.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private BusService busService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value= {"/bus"})
    public String Bus() {
        return "bus";
    }
    
    @PostMapping("/search")
    public String searchBuses(Model model, @RequestParam("source") String source,
                              @RequestParam("destination") String destination,
                              @RequestParam("date") String dateStr) throws Exception {

        LocalDate date = LocalDate.parse(dateStr);
        List<Bus> buses = busService.findBusesByRouteAndDate(source, destination, date);
        model.addAttribute("buses", buses);
        if (buses.isEmpty()) {
	        model.addAttribute("message","no buses available for the selected route");            
        }
        return "bus";
    }

    
    @GetMapping("/book/{id}")
    public String showBookingPage(@PathVariable("id") Long id, Model model) {
        Bus bus = busService.getBusById(id);
        model.addAttribute("bus", bus);
        model.addAttribute("booking", new Booking());
        return "booking";
    }
    
    @PostMapping("/bookings")
    public String saveBooking(@Valid Booking booking, @RequestParam Long busId,
            @RequestParam Double totalPrice, @RequestParam Integer numTickets,
            Model model, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            User loggedUser = getLoggedUser(principal);
            if (loggedUser != null) {
                Long userId = loggedUser.getId();
                Booking savedBooking = bookingService.saveBooking(booking, userId, busId, numTickets, totalPrice);
               
                Long bookingId = savedBooking.getBookingId();
                System.out.println(bookingId);
                redirectAttributes.addAttribute("bookingId", bookingId);

                return "payment";
            } else {
                throw new Exception("Error occurred. User not found.");
            }
        } catch (Exception e) {
            // Set the error message
            model.addAttribute("errorMessage", e.getMessage());
            return "booking";
        }
    }


    public User getLoggedUser(Principal principal) {
        User loggedUser = userService.findByEmail(principal.getName());
        return loggedUser;
    }

}

