package com.bevelop.devbevelop.domain.user.repository;

import com.bevelop.devbevelop.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findBySocialIdAndProvider(String socialId, String provider);
    Optional<User> findBySocialId(String socialId);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    User save(User user);
}