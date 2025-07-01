package com.example.MakeMyTrip;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cexchange_new {
    public cexchange_new() {
    }

    @GetMapping({"/cexchange_new"})
    public String getData() {
        return "Please book your cexchange_new from Indigo kindly book ticket for New Delhi to anywhere at 10% discount";
    }
}
