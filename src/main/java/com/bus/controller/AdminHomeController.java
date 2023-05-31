package com.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin")
public class AdminHomeController {


    @GetMapping("/home")
    public String showHomePage() {
        return "admin/home";
    }

}
