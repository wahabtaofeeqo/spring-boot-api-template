package com.example.template.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.template.exceptions.AccountExistException;
import com.example.template.models.Role;
import com.example.template.models.User;
import com.example.template.repositories.RoleRepository;
import com.example.template.repositories.UserRepository;
import com.example.template.requests.LoginDTO;
import com.example.template.requests.RegisterDTO;
import com.example.template.responses.ErrResponse;
import com.example.template.responses.OkResponse;
import com.example.template.security.JWTUtil;
import com.example.template.services.UserDetailsImpl;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrResponse("Username OR Password not correct", e.getMessage()));
        }

        UserDetailsImpl detailsImpl = (UserDetailsImpl)  authentication.getPrincipal();
        String token = jwtUtil.generateToken(detailsImpl.getUser());
       
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", detailsImpl.getUser());

        //
        return ResponseEntity.ok().body(new OkResponse("Login successful", data));
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {

        // Check 
        User user = this.userRepository.findByEmail(dto.getEmail());
        if(user != null) throw new AccountExistException(dto.getEmail());

        Role role = roleRepository.findByName("ROLE_USER").orElse(
            roleRepository.save(new Role("ROLE_USER"))
        );

        // Prep
        user = dto.toUser();
        user.setRoles(Arrays.asList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //
        user = userRepository.save(user);

        //
        return ResponseEntity.ok().body(new OkResponse("Created successfully", user));
    }
}
