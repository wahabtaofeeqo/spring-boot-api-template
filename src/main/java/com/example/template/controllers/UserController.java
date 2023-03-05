package com.example.template.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    @GetMapping("")
    public ResponseEntity<?> profile() {

        Map<String, Object> data = new HashMap<>();
        data.put("user", this.getUser());
        return this.okResponse("Profile", data);
    }
}
