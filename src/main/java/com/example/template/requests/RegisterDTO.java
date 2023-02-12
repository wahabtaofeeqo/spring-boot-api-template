package com.example.template.requests;

import com.example.template.models.User;

import lombok.Data;

@Data
public class RegisterDTO {
    
    private String name;
    private String email;
    private String password;

    public User toUser() {
        return new User(name, email, password);
    }
}
