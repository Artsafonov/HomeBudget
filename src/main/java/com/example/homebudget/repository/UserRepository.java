package com.example.homebudget.repository;

import com.example.homebudget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);

}