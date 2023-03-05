package com.example.template.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty
    @Size(min = 6)
    private String username;

    @NotEmpty
    @Size(min = 6)
    private String password;
}
