package org.flavius.controller;

import org.flavius.entity.UserEntity;
import org.flavius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String allUsers() {
        String users = userService.findAll().stream()
                .map(UserEntity::getUsername)
                .collect(Collectors.joining(" , "));
        return users;
    }

}