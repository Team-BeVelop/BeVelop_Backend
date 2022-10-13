package com.bevelop.devbevelop.domain.project.repository;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.domain.ProjectResponse;
import com.bevelop.devbevelop.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ProjectLikeRepository extends JpaRepository<ProjectResponse, Long> {
    Optional<ProjectResponse> findByUserAndProject(User user, Project project);

    List<ProjectResponse> findByProjectId(Long projectId);
}