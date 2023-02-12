package com.example.template.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.template.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    
    User findByEmail(String email);
}
