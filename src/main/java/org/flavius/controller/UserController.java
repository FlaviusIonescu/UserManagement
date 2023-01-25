package org.flavius.controller;

import org.flavius.dto.ChangePasswordDto;
import org.flavius.dto.UserDto;
import org.flavius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<UserDto> allUsers() {
        return userService.findAll();
    }

    @PostMapping("/")
    void createUser(@RequestBody UserDto userDto) {
        userService.create(userDto.getUsername(), userDto.getPassword());
    }

    @PostMapping("/toggle/")
    void toggleUser(@RequestBody String username) {
        userService.toggleStatus(username);
    }

    @PostMapping("/change/")
    void changePassword(@RequestBody ChangePasswordDto pass) {
        userService.updatePassword(pass.getUsername(), pass.getOldPassword(), pass.getNewPassword(), pass.getConfirmedPassword());
    }

    @DeleteMapping("/{username}")
    void deleteUser(@RequestParam String username) {
        userService.delete(username);
    }

}