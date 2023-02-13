package com.example.template.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") 
    private String sender;

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public boolean sendPlain(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(sender);
            message.setTo(to); 
            message.setSubject(subject); 
            message.setText(text);
            mailSender.send(message);

            return true;
        } 
        catch (Exception e) {
            logger.info(e.getMessage());
        }

        return false;
    }
}
