package com.bus.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bus.model.User;
import com.bus.service.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
    @RequestMapping(value= {"/","/home"})
    public String home() {
        return "home";
    }
    
    @GetMapping("/profile")
    public String showProfilePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }
  
}