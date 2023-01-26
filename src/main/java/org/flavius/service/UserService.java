package org.flavius.service;

import org.flavius.dto.UserDto;
import org.flavius.entity.UserEntity;
import org.flavius.exception.PasswordMismatchException;
import org.flavius.exception.UserNotFoundException;
import org.flavius.exception.WrongCredentialsException;

import java.util.List;

public interface UserService {
    // TODO use user DTOs for return
    UserEntity findByUserName(String username) throws UserNotFoundException;

    List<UserDto> findAll();

    void create(String username, String password);

    void updatePassword(String username, String oldPassword, String newPassword, String confirmedPassword)
            throws PasswordMismatchException, UserNotFoundException, WrongCredentialsException;

    void toggleStatus(String username) throws UserNotFoundException;

    void delete(String username) throws UserNotFoundException;
}
