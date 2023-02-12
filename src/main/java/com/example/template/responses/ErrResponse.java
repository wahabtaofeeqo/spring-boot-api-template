package com.example.template.responses;

import lombok.Data;

@Data
public class ErrResponse {

    private boolean status = false;
    private String message;
    private Object error;

    public ErrResponse(String message) {
        this.message = message;
    }

    public ErrResponse(String message, Object error) {
        this.message = message;
        this.error = error;
    }
}
