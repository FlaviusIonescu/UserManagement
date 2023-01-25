package org.flavius.service;

import org.flavius.entity.Status;
import org.flavius.entity.UserEntity;
import org.flavius.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void create(String username, String password) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        // TODO - BCryptPasswordEncoder for password encoding
        entity.setPassword(password);
        entity.setStatus(Status.ACTIVE);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void toggleStatus(String username, Status status) {
        UserEntity entity = userRepository.findByUsername(username);
        if (entity != null) {
            entity.setStatus(status);
        }
        // TODO - handle not found
    }

    @Override
    @Transactional
    public void delete(String username) {
        UserEntity entity = userRepository.findByUsername(username);
        if (entity != null) {
            userRepository.delete(entity);
        }
        // TODO - handle not found
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        UserEntity entity = userRepository.findByUsername(username);
        if (entity != null) {
            // TODO - BCryptPasswordEncoder for password encoding
            entity.setPassword(password);
        }
        // TODO - handle not found
    }

}
