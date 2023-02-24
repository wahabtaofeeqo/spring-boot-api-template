package com.example.template.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
	private MockMvc mockMvc;

    @Test
    void testLoginShouldReturnValidationError400() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")).andReturn();

        assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    void testRegisterShouldReturnValidationError400() throws Exception {
        MvcResult result = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")).andReturn();

        assertEquals(result.getResponse().getStatus(), 400);
    }
}
