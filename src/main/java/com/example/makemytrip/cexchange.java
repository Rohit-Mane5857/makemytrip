package com.example.MakeMyTrip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cexchange {
    public cexchange() {
    }

    @GetMapping({"/cexchange"})
    public String getData() {
        return "Please exchange your currency at best rate";
    }
}