package com.example.MakeMyTrip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hotel {
    public hotel() {
    }

    @GetMapping({"/hotel"})
    public String getData() {
        return "Please book your hotel from makemytrip kindly book ticket for New Delhi to anywhere at 25% discount";
    }
}
