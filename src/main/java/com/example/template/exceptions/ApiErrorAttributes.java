package com.example.template.exceptions;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

        Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);

        // Remeve keys
        attributes.remove("path");
        attributes.remove("timestamp");

        // Add keys
        attributes.put("status", false);
        String error = (String) attributes.get("message");
        String message = (String) attributes.get("error");
    
        attributes.put("error", error);
        attributes.put("message", message);

        //
        return attributes;
    }
}