package com.app.parking.repository;

import com.app.parking.entity.User;
import com.app.parking.exception.custom_exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username) throws UserNotFoundException;
}
