package org.flavius.service;

import org.flavius.dto.UserDto;
import org.flavius.entity.UserEntity;

import java.util.List;

public interface UserService {
    // TODO use user DTOs for return
    UserEntity findByUserName(String username);

    List<UserDto> findAll();

    void create(String username, String password);

    void updatePassword(String username, String oldPassword, String newPassword, String confirmedPassword);

    void toggleStatus(String username);

    void delete(String username);
}
