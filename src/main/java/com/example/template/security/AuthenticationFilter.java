package com.example.template.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.template.services.UserDetailsServiceImpl;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl detailsImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String token = this.getToken(request);
            if(token != null && jwtUtil.isTokenValid(token)) {

                String username = jwtUtil.getUsername(token);
                UserDetails user = detailsImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(user);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        doFilter(request, response, filterChain);
    }

    private String getToken(HttpServletRequest request) {

        String token = null;
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")) {
            token = header.substring("Bearer ".length());
        }

        return token;
    }
}
