package com.lcwd.electronic.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HomeController {
    @GetMapping
    public String testing() {
        //We are able to send String from here because we have used @RestController Annotation, if we had used only
        //@Controller annotation, we would have written @ResponseBody with each @RequestMapping to send String else
        //create view ----> string_name.html
        return "Welcome to Electronic Store.";
    }
}
