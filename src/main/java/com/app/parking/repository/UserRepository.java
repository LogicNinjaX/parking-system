package com.app.parking.repository;

import com.app.parking.entity.User;
import com.app.parking.entity.Wallet;
import com.app.parking.exception.custom_exception.UserNotFoundException;
import com.app.parking.exception.custom_exception.WalletNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT new com.app.parking.entity.User(u.userId, u.username, u.password, u.role) FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username) throws UserNotFoundException;

    @Query("SELECT u.wallet FROM User u WHERE u.userId = :userId")
    Optional<Wallet> findWalletByUserId(UUID userId) throws WalletNotFoundException;
}
