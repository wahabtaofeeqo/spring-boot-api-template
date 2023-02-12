package com.example.template.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.template.models.Role;

public interface RoleRepository extends CrudRepository<Role, UUID> {
    
    Optional<Role> findByName(String name);
}
