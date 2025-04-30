package com.example.MakeMyTrip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class bus {
    public bus() {
    }

    @GetMapping({"/bus"})
    public String getData() {
        return "Please book your bus from makemytrip kindly book ticket for New Delhi to anywhere at 10% discount";
    }
}
