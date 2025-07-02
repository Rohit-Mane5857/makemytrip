package com.example.MakeMyTrip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class abcd {
    public abcd() {
    }

    @GetMapping({"/abcd"})
    public String getData() {
        return "Please book your abcd from makemytrip kindly book ticket for New Delhi to anywhere at 10% discount";
    }
}
