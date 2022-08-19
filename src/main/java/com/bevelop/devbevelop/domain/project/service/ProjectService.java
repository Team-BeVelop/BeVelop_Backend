package com.bevelop.devbevelop.domain.project.service;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.ProjectMapper;
import com.bevelop.devbevelop.domain.project.dto.ProjectUpdate;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    //프로젝트 생성
    public Project create(Project project) {
        validateDuplicateTitle(project);
        return projectRepository.save(project);
    }

    //중복 제목 체크
    private void validateDuplicateTitle(Project project) {
        Optional<Project> findProject = projectRepository.findByTitle(project.getTitle());
        if(findProject.isPresent()) throw new CustomException(ErrorCode.PROJECT_EXISTS);
    }

    //title로 프로젝트 찾기
    public Optional<Project> findByTitle(String title) {
        Optional<Project> findProject = projectRepository.findByTitle(title);
        if(findProject.isEmpty()) throw new CustomException(ErrorCode.PROJECT_NOT_FOUND);
        return findProject;
    }

    //update project
    public Project updateProject(String title, ProjectUpdate projectForm) {
        Project original = findByTitle(title).get();
        projectMapper.updateProjectFromDto(projectForm, original);
        return projectRepository.save(original);
    }

    //delete project
    public void deleteProject(String title) {
        projectRepository.deleteById(findByTitle(title).get().getId());
    }


}
