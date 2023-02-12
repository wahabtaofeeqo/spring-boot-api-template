package com.example.template.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.template.models.User;
import com.example.template.responses.ErrResponse;
import com.example.template.responses.OkResponse;
import com.example.template.services.UserDetailsImpl;

public class BaseController {
    
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        return details.getUser();
    }
    
    public ResponseEntity<?> okResponse(String message, Object data) {
        return ResponseEntity.ok().body(new OkResponse(message, data));
    }
    
    public ResponseEntity<?> errResponse(String message, Object error) {
        return ResponseEntity.ok().body(new ErrResponse(message, error));
    }
}
