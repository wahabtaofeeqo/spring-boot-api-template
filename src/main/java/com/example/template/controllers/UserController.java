package com.example.template.controllers;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.template.models.User;
import com.example.template.responses.OkResponse;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    @GetMapping("")
    public ResponseEntity<?> profile() {
        User user = this.getUser();
        return ResponseEntity.ok().body(new OkResponse("Profile!", user));
    }
}
