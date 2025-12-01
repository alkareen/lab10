package com.example.lab9.controllers;

import com.example.lab9.services.CountryService;
import com.example.lab9.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;
    private final CountryService countryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("countries", countryService.findAll());
        return "home";
    }

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @GetMapping("/countries")
    public String countries(Model model) {
        model.addAttribute("countries", countryService.findAll());
        return "countries";
    }
}