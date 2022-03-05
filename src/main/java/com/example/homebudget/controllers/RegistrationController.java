package com.example.homebudget.controllers;


import com.example.homebudget.entity.Role;
import com.example.homebudget.entity.User;
import com.example.homebudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "/Registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "Registration";
        }
        user.setEnabled(true);
        user.setRoles(Set.of(Role.User));

        userRepository.save(user);
        return "redirect:/";
    }



}