package com.app.parking.service.impl;

import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.entity.User;
import com.app.parking.exception.custom_exception.FieldUniqueException;
import com.app.parking.mapper.UserMapper;
import com.app.parking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest request;
    private User user;
    private RegisterResponse response;
    private UUID userId = UUID.randomUUID();


    @BeforeEach
    void setUp() {
        request = new RegisterRequest();
        request.setUsername("testUser");
        request.setEmail("test@mail.com");
        request.setPassword("password");

        user = new User();
        user.setUserId(userId);
        user.setUsername("testUser");

        response = new RegisterResponse();
    }

    @Test
    void registerUser_success() throws Exception {
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        RegisterResponse result = userService.registerUser(request);

        assertNotNull(result);
        verify(userRepository).save(user);
        verify(passwordEncoder).encode(request.getPassword());
    }


    @Test
    void registerUser_usernameAlreadyExists() {
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("uk_user_table_username"));

        FieldUniqueException ex = assertThrows(
                FieldUniqueException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("username must be unique", ex.getMessage());
    }

    @Test
    void registerUser_emailAlreadyExists() {
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("uk_user_table_email"));

        FieldUniqueException ex = assertThrows(
                FieldUniqueException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("email must be unique", ex.getMessage());
    }

    @Test
    void registerUser_unknownFieldViolation() {
        when(userMapper.toUser(request)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encoded");

        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("some_other_error"));

        FieldUniqueException ex = assertThrows(
                FieldUniqueException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("field must be unique", ex.getMessage());
    }

    @Test
    void getUserByUsername_success() {
        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }


    @Test
    void getUserByUsername_notFound() {
        when(userRepository.findByUsername("testUser"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userService.getUserByUsername("testUser")
        );
    }

}
