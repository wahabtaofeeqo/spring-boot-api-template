package com.example.template.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginDTO {

    @NotNull
    @NotEmpty
    @Min(value = 3)
    private String username;

    @NotNull
    @NotEmpty
    @Min(value = 6)
    private String password;
}
