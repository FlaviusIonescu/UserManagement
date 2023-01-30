package org.flavius.service;

import org.flavius.dto.UserDto;
import org.flavius.entity.Status;
import org.flavius.entity.UserEntity;
import org.flavius.exception.PasswordMismatchException;
import org.flavius.exception.UserAlreadyExistsException;
import org.flavius.exception.UserNotFoundException;
import org.flavius.exception.WrongCredentialsException;
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
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    protected UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity findByUserName(String username) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return userEntity;
        }
        throw new UserNotFoundException(username);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(u -> {
                    UserDto dto = new UserDto();
                    dto.setUsername(u.getUsername());
                    dto.setStatus(u.isEnabled() ? Status.ACTIVE.name() : Status.DISABLED.name());
                    return dto;
                }).sorted(Comparator.comparing(UserDto::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserEntity create(String username, String password) throws UserAlreadyExistsException {
        UserEntity check = userRepository.findByUsername(username);
        if (check != null) {
            throw new UserAlreadyExistsException(username);
        }
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setEnabled(true);
        entity.setRole("ROLE_ADMIN");
        return userRepository.save(entity);
    }

    @Override
    @Transactional
    public UserEntity toggleStatus(String username) throws UserNotFoundException {
        UserEntity entity = this.findByUserName(username);
        entity.setEnabled(!entity.isEnabled());
        return entity;
    }

    @Override
    @Transactional
    public void delete(String username) throws UserNotFoundException {
        UserEntity entity = this.findByUserName(username);
        userRepository.delete(entity);
    }

    @Override
    @Transactional
    public UserEntity updatePassword(String username, String oldPassword, String newPassword, String confirmedPassword)
            throws UserNotFoundException, PasswordMismatchException, WrongCredentialsException {
        if (!confirmedPassword.equals(newPassword)) {
            throw new PasswordMismatchException();
        }
        UserEntity entity = this.findByUserName(username);
        if (!passwordEncoder.matches(oldPassword, entity.getPassword())) {
            throw new WrongCredentialsException();
        }
        // save password only if the new one is different
        if (!passwordEncoder.matches(newPassword, entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(newPassword));
        }
        return entity;
    }

}
