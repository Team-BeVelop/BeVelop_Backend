package com.bevelop.devbevelop.domain.user.repository;

import com.bevelop.devbevelop.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findBySocialIdAndProvider(String socialId, String provider);

    Optional<User> findByEmailAndProvider(String email, String provider);
    boolean existsByEmail(String email);
}