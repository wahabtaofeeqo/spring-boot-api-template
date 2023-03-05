package com.example.template.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.template.models.User;

import lombok.Data;

@Data
public class RegisterDTO {
    
    @NotEmpty
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6)
    private String password;

    public User toUser() {
        return new User(name, email, password);
    }
}
