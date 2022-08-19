package com.bevelop.devbevelop.domain.project.repository;

import com.bevelop.devbevelop.domain.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByTitle(String title);
    Optional<Project> findById(Long id);
    Project save(Project project);
}
