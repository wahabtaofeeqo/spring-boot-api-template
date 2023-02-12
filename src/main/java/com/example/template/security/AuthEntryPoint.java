package com.example.template.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.template.responses.ErrResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");

        OutputStream stream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        ErrResponse error = new ErrResponse("Unauthorized");
        mapper.writeValue(stream, error);

        //
        stream.flush();
    }
}
