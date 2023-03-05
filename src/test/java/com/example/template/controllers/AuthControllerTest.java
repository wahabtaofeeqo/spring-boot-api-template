package com.example.template.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.example.template.models.Role;
import com.example.template.models.User;
import com.example.template.repositories.RoleRepository;
import com.example.template.repositories.UserRepository;
import com.example.template.requests.LoginDTO;
import com.example.template.requests.RegisterDTO;
import com.example.template.security.JWTUtil;
import com.example.template.services.UserDetailsImpl;
import com.example.template.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserRepository repository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImpl detailsServiceImpl;

    @MockBean
    private JWTUtil jwtUtil;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setName("Taofeek");
        user.setEmail("tao@yahoo.com");
    }

    @Test
    void testLogin_invalidPayload_shouldFailWith400() throws Exception {

        MvcResult result = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON).content("{}")).andReturn();

        assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    void testLogin_validPayload_shouldPassWith200() throws Exception {

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        Authentication authentication = mock(Authentication.class);

        when(userDetails.getUser()).thenReturn(user);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken(user)).thenReturn("loremipsumtoken");
        when(authenticationManager.authenticate(any())).thenReturn(authentication); 
      
        // Request
        LoginDTO dto = new LoginDTO("tao@yahoo.com", "password");
        ResultActions actions = mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)));

        // Asserts
        assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        actions.andExpect(jsonPath("$.data.token", is(notNullValue())));
        actions.andExpect(jsonPath("$.message", is("Login successful")));
    }

    @Test
    void testRegister_invalidPayload_shouldFailWith400() throws Exception {

        MvcResult result = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content("{}")).andReturn();

        assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    void testRegister_ExistingEmail_shouldFailWith400() throws Exception {

        // Payload
        RegisterDTO dto = new RegisterDTO();
        dto.setName("Taofeek");
        dto.setEmail("tao@yahoo.com");
        dto.setPassword("password");
        
        // When
        when(repository.findByEmail(dto.getEmail())).thenReturn(user);

        // Request
        ResultActions actions = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)));

        // Asserts
        assertEquals(actions.andReturn().getResponse().getStatus(), 400);
        actions.andExpect(jsonPath("$.message", is("Account already exist")));
    }

    @Test
    void testRegister_validPayload_shouldPassWith200() throws Exception {

        // Payload
        RegisterDTO dto = new RegisterDTO();
        dto.setName("Taofeek");
        dto.setEmail("tao@yahoo.com");
        dto.setPassword("password");

        Optional<Role> optional = Optional.of(new Role());

        when(repository.save(dto.toUser())).thenReturn(user);
        when(roleRepository.findByName(anyString())).thenReturn(optional);

        ResultActions actions = mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)));

        //
        assertEquals(actions.andReturn().getResponse().getStatus(), 200);
        actions.andExpect(jsonPath("$.message", is("Created successfully")));
    }
}
