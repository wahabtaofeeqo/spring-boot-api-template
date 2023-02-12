package com.example.template.responses;

import lombok.Data;

@Data
public class OkResponse {
    
    private boolean status = true;
    private String message;
    private Object data;

    public OkResponse(String message) {
        this.message = message;
    }

    public OkResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
