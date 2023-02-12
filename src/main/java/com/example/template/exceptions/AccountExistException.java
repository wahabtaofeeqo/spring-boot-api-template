package com.example.template.exceptions;

public class AccountExistException extends RuntimeException {
    
    private String email;

    public AccountExistException() {}

    public AccountExistException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
