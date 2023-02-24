package com.example.template.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.template.models.User;

import lombok.Data;

@Data
public class RegisterDTO {
    
    @NotNull
    @NotEmpty
    private String name;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Min(value = 6)
    private String password;

    public User toUser() {
        return new User(name, email, password);
    }
}
