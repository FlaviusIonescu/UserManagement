package org.flavius.controller;


import org.flavius.dto.UserDto;
import org.flavius.exception.UserAlreadyExistsException;
import org.flavius.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    private UserDto createUser(String username, String password) {
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(password);
        user.setStatus("ACTIVE");
        return user;
    }

    @Test
    public void should_create_user() throws UserAlreadyExistsException {
        // given
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setPassword("pass1");

        //when
        userController.createUser(userDto);

        //no exceptions
    }

    @Test
    public void should_find_all_users() {
        // given
        when(userService.findAll()).thenReturn(List.of(createUser("first", "pass1"), createUser("second", "pass2")));

        //when
        List<UserDto> users = userController.allUsers();

        //then
        assertEquals(2, users.size());
        assertEquals("first", users.get(0).getUsername());
        assertEquals("second", users.get(1).getUsername());
    }

}