package com.bevelop.devbevelop.domain.project.service;

import org.springframework.util.StringUtils;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.ProjectRes;
import com.bevelop.devbevelop.domain.project.dto.ProjectUpdate;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    //중복 제목 체크
    private void validateDuplicateTitle(Project project) {
        Optional<Project> findProject = projectRepository.findByTitle(project.getTitle());
        if(findProject.isPresent()) throw new CustomException(ErrorCode.PROJECT_EXISTS);
    }

    //title로 프로젝트 찾기
    public ProjectRes findByTitle(String title) {
        Optional<Project> findProject = projectRepository.findByTitle(title);
        return new ProjectRes(findProject.orElseThrow(()->new CustomException(ErrorCode.PROJECT_NOT_FOUND)));
    }

    public ProjectRes findById(Long id) {
        Optional<Project> findProject = projectRepository.findById(id);
        return new ProjectRes(findProject.orElseThrow(()->new CustomException(ErrorCode.PROJECT_NOT_FOUND)));
    }

    //update project
    public Project save(Project project) {
        return projectRepository.save(project);
    }

                          //delete project
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }
}
