package org.flavius.service;

import org.flavius.dto.UserDto;
import org.flavius.entity.Status;
import org.flavius.entity.UserEntity;
import org.flavius.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(u -> {
                    UserDto dto = new UserDto();
                    dto.setUsername(u.getUsername());
                    dto.setStatus(u.getStatus().name());
                    return dto;
                }).sorted(Comparator.comparing(UserDto::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void create(String username, String password) {
        //TODO - email matcher for username
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setStatus(Status.ACTIVE);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void toggleStatus(String username) {
        UserEntity entity = userRepository.findByUsername(username);
        if (entity != null) {
            if (entity.getStatus().equals(Status.ACTIVE)) {
                entity.setStatus(Status.DISABLED);
            } else {
                entity.setStatus(Status.ACTIVE);
            }
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
    public void updatePassword(String username, String oldPassword, String newPassword, String confirmedPassword) {
        if (!confirmedPassword.equals(newPassword)) {
            // TODO - throw exception
        }
        UserEntity entity = userRepository.findByUsername(username);
        if (entity != null) {
            if (!passwordEncoder.matches(newPassword, entity.getPassword())) {
                entity.setPassword(passwordEncoder.encode(newPassword));
            }
        }
        // TODO - handle not found
    }

}
