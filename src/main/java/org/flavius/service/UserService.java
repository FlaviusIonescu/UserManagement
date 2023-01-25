package org.flavius.service;

import org.flavius.entity.Status;
import org.flavius.entity.UserEntity;

import java.util.List;

public interface UserService {
    // TODO use user DTOs for return
    UserEntity findByUserName(String username);

    List<UserEntity> findAll();

    void create(String username, String password);

    void updatePassword(String username, String password);

    void toggleStatus(String username, Status status);

    void delete(String username);
}
