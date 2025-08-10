package com.app.parking.service.impl;

import com.app.parking.dto.request.RegisterRequest;
import com.app.parking.dto.response.RegisterResponse;
import com.app.parking.entity.User;
import com.app.parking.entity.Wallet;
import com.app.parking.enums.UserRole;
import com.app.parking.exception.custom_exception.FieldUniqueException;
import com.app.parking.mapper.UserMapper;
import com.app.parking.repository.UserRepository;
import com.app.parking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest request) throws FieldUniqueException {
        User user = userMapper.toUser(request);
        try {
            user.setRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setWallet(new Wallet()); // assigning a new wallet
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("uk_user_table_username")) {
                throw new FieldUniqueException("username");
            } else if (e.getMessage().contains("uk_user_table_email")){
                throw new FieldUniqueException("email");
            }else {
                throw new FieldUniqueException("field");
            }
        }
        LOGGER.info("User saved with user id: {} and username: {}", user.getUserId(), user.getUsername());
        return userMapper.toResponse(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
