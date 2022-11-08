package com.mprog.http.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class MainController {

    @GetMapping("/one")
    public String get() {
        return "get-one";
    }
}

