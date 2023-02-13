package com.example.template.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    
    @Autowired
    private RestTemplate template;

    public ResponseEntity<String> get(String url) {
        return template.getForEntity(url, String.class);
    }

    public ResponseEntity<String> post(String url, Object data) {
        return template.postForEntity(url, data, String.class);
    }

    public void put(String url, Object data) {
        template.put(url, data);
    }

    public void delete(String url) {
        template.delete(url);
    }
}
