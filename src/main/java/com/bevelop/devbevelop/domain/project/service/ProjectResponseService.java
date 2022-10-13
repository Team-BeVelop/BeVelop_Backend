package com.bevelop.devbevelop.domain.project.service;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.domain.ProjectResponse;
import com.bevelop.devbevelop.domain.project.domain.Responses;
import com.bevelop.devbevelop.domain.project.repository.ProjectLikeRepository;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProjectResponseService {
    private final ProjectLikeRepository likeRepository;

    public boolean addResponse(User user, Project project, Responses response) {

        //중복 좋아요 방지
        if(isNotAlreadyLike(user, project)) {
            likeRepository.save(new ProjectResponse(user, project, response));
            return true;
        }

        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(User user, Project project) {
        return likeRepository.findByUserAndProject(user, project).isEmpty();
    }

    @Transactional
    public List<ProjectResponse> findAll(Long projectId) {
        return likeRepository.findByProjectId(projectId);
//        return projectRepository.findById(projectId).get().getComments();
    }
}