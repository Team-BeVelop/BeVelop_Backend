package com.bevelop.devbevelop.domain.project.controller;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.domain.ProjectResponse;
import com.bevelop.devbevelop.domain.project.domain.Responses;
import com.bevelop.devbevelop.domain.project.repository.ProjectRepository;
import com.bevelop.devbevelop.domain.project.service.ProjectResponseService;
import com.bevelop.devbevelop.domain.project.service.ProjectService;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.common.response.CommonResult;
import com.bevelop.devbevelop.global.common.response.ResponseService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Project Controller"})
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectLikeController {
    private final ProjectResponseService likeService;
    private final ProjectService projectService;
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "새로운 공감 추가")
    @PostMapping("response/new/{projectId}")
    public CommonResult addLike(@PathVariable Long projectId, @RequestParam Responses response) throws CustomException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User signedInUser =  userService.findBySocialIdOrEmail(authentication.getName()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Project project = projectService.findById(projectId);

        return likeService.addResponse(signedInUser, project, response)?
                responseService.getSuccessResult() : responseService.getFailResult(500, "Could not press like");
    }

    @ApiOperation(value = "프로젝트 공감 모두 불러오기")
    @GetMapping("responses/{projectId}")
    public List<ProjectResponse> responseList(@PathVariable Long projectId) throws CustomException {
        return likeService.findAll(projectId);
    }
}
