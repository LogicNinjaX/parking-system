package com.app.parking.service.impl;

import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.entity.User;
import com.app.parking.enums.UserRole;
import com.app.parking.exception.custom_exception.FieldUniqueException;
import com.app.parking.mapper.UserMapper;
import com.app.parking.repository.UserRepository;
import com.app.parking.service.UserService;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws FieldUniqueException {
        User user = userMapper.toUser(request);
        try {
            user.setRole(UserRole.USER);
            user = userRepository.save(user);
        } catch (ConstraintViolationException e) {
            if (e.getMessage().contains("uk_user_table_username")) {
                throw new FieldUniqueException("username");
            } else {
                throw new FieldUniqueException("field");
            }
        }
        LOGGER.info("User saved with user id: {} and username: {}", user.getUserId(), user.getUsername());
        return userMapper.toResponse(user);
    }
}
