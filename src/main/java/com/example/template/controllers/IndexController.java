package com.example.template.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.template.responses.OkResponse;

@RestController
public class IndexController {

    @GetMapping("/")
    public ResponseEntity<Object> welcome() {
        return ResponseEntity.ok().body(new OkResponse("Welcome! Let's start."));
    }
}
