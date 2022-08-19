package com.bevelop.devbevelop.domain.team.repository;

import com.bevelop.devbevelop.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTitle(String title);
}
