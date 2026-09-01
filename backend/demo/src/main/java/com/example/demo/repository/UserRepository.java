package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContaining(String name);
    
    boolean existsByEmail(String email);
    
    boolean existsByEmailAndIdNot(String email, Integer id);
}
