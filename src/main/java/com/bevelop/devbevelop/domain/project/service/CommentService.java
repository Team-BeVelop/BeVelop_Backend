package com.bevelop.devbevelop.domain.project.service;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.CommentReq;
import com.bevelop.devbevelop.domain.project.dto.CommentRes;
import com.bevelop.devbevelop.domain.project.repository.CommentRepository;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    /* CREATE */
    @Transactional
    public CommentRes commentSave(Long userId, Long projectId, CommentReq dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        Comment comment = dto.toEntity();
        comment.setUser(user);
        comment.setProject(project);
        commentRepository.save(comment);
        return new CommentRes(comment);
    }

    @Transactional
    public List<Comment> findAll(Long projectId) {
        return commentRepository.findByProjectId(projectId);
//        return projectRepository.findById(projectId).get().getComments();
    }
}
