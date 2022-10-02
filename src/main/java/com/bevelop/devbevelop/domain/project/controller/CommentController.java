package com.bevelop.devbevelop.domain.project.controller;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import com.bevelop.devbevelop.domain.project.dto.CommentReq;
import com.bevelop.devbevelop.domain.project.dto.CommentRes;
import com.bevelop.devbevelop.domain.project.service.CommentService;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(tags = {"3. Project Controller"})
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    /* CREATE */
    @ApiOperation(value = "새로운 댓글 추가")
    @PostMapping("{projectId}/comments")
    public CommentRes commentSave(@PathVariable Long projectId, @RequestBody CommentReq dto) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  userService.findBySocialIdOrEmail(authentication.getName()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return commentService.commentSave(user.getId(), projectId, dto);
    }

    @ApiOperation(value = "프로젝트 댓글 모두 불러오기")
    @GetMapping("comments/{projectId}")
    public Set<Comment> commentList(@PathVariable Long projectId) throws CustomException {
        return commentService.findAll(projectId);
    }
}