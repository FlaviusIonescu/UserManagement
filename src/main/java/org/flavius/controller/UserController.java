package org.flavius.controller;

import org.flavius.dto.ChangePasswordDto;
import org.flavius.dto.LoginDto;
import org.flavius.dto.UserDto;
import org.flavius.exception.PasswordMismatchException;
import org.flavius.exception.UserNotFoundException;
import org.flavius.exception.WrongCredentialsException;
import org.flavius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestBody LoginDto loginDto) throws UserNotFoundException {

        userService.findByUserName(loginDto.getUsername());
        int a = 0;
        a++;
        return  true;

    }

    @GetMapping("")
    public List<UserDto> allUsers() {
        return userService.findAll();
    }

    @PostMapping("")
    void createUser(@RequestBody UserDto userDto) {
        userService.create(userDto.getUsername(), userDto.getPassword());
    }

    @PostMapping("/toggle")
    void toggleUser(@RequestBody String username) throws UserNotFoundException {
        userService.toggleStatus(username);
    }

    @PostMapping("/change")
    void changePassword(@RequestBody ChangePasswordDto pass)
            throws PasswordMismatchException, UserNotFoundException, WrongCredentialsException {
        userService.updatePassword(pass.getUsername(), pass.getOldPassword(), pass.getNewPassword(), pass.getConfirmedPassword());
    }

    @DeleteMapping("/delete")
    void deleteUser(@RequestParam String username) throws UserNotFoundException {
        userService.delete(username);
    }

}