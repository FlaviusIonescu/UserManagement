package org.flavius.service;

import org.flavius.entity.UserEntity;
import org.flavius.exception.PasswordMismatchException;
import org.flavius.exception.UserNotFoundException;
import org.flavius.exception.WrongCredentialsException;
import org.flavius.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    private UserEntity createUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("some role");
        user.setEnabled(true);
        return user;
    }

    @Test
    public void should_find_one_user() throws UserNotFoundException {
        // given
        final var userSaved = createUser("user1", "pass1");
        when(userRepository.findByUsername(anyString())).thenReturn(userSaved);

        // when
        final var actual = userService.findByUserName("user1");

        // then
        assertNotNull(actual);
        verify(userRepository, Mockito.times(1)).findByUsername(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void should_throw_user_not_found() {
        // when
        assertThrows(UserNotFoundException.class, () -> userService.findByUserName("user2"));
    }

    @Test
    public void should_find_some_users() {
        // given
        final var user1 = createUser("user1", "pass1");
        final var user2 = createUser("user2", "pass2");
        var users = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        // when
        final var foundUsers = userService.findAll();

        // then
        assertNotNull(foundUsers);
        verify(userRepository, Mockito.times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
        assertEquals(2, foundUsers.size());
    }

    @Test
    public void should_create_one_user() {
        // given
        final var saved = createUser("user1", "pass1");
        when(userRepository.save(any())).thenReturn(saved);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encoded_password");

        // when
        final var actual = userService.create("user1", "pass1");

        // then
        assertNotNull(actual);
        assertEquals(saved.getUsername(), actual.getUsername());
        verify(userRepository, Mockito.times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void should_change_password() throws UserNotFoundException, PasswordMismatchException, WrongCredentialsException {
        // given
        final var saved = createUser("user1", "encoded_password_pass1");
        when(userRepository.findByUsername(any())).thenReturn(saved);

        when(passwordEncoder.matches("pass1", "encoded_password_pass1")).thenReturn(true);
        when(passwordEncoder.encode("pass2")).thenReturn("encoded_password_pass2");

        // when
        final var actual = userService.updatePassword("user1", "pass1", "pass2", "pass2");

        // then
        assertNotNull(actual);
        assertEquals(saved.getUsername(), actual.getUsername());
        assertEquals("encoded_password_pass2", actual.getPassword());
        verifyNoMoreInteractions(userRepository);
    }

    @Test()
    public void should_throw_credentials_mismatch() {
        // given
        final var saved = createUser("user1", "encoded_password_pass1");
        when(userRepository.findByUsername(any())).thenReturn(saved);

        when(passwordEncoder.matches("pass3", "encoded_password_pass1")).thenReturn(false);

        // when
        assertThrows(WrongCredentialsException.class,
                () -> userService.updatePassword("user1", "pass3", "pass2", "pass2"));
    }

    @Test()
    public void should_throw_password_mismatch() {
        // when
        assertThrows(PasswordMismatchException.class,
                () -> userService.updatePassword("user1", "pass1", "pass2", "pass3"));
    }

    @Test
    void should_delete_one_user() throws UserNotFoundException {
        // given
        final var saved = createUser("user1", "encoded_password_pass1");
        when(userRepository.findByUsername(any())).thenReturn(saved);
        doNothing().when(userRepository).delete(any());

        // when
        userService.delete("user1");

        //then
        verify(userRepository, times(1)).delete(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void should_throw_user_not_found_when_delete_one_user() {
        // when
        assertThrows(UserNotFoundException.class,
                () -> userService.delete("user1"));
    }

    @Test
    public void should_toggle_active() throws UserNotFoundException {
        // given
        final var saved = createUser("user1", "encoded_password_pass1");
        when(userRepository.findByUsername(any())).thenReturn(saved);

        // when
        final var actual = userService.toggleStatus("user1");

        // then
        assertNotNull(actual);
        assertEquals(saved.getUsername(), actual.getUsername());
        assertFalse(actual.isEnabled());
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    public void should_throw_user_not_found_toggle_active() {
        // when
        assertThrows(UserNotFoundException.class,
                () -> userService.toggleStatus("user1"));
    }
}
