package com.bevelop.devbevelop.domain.project.controller;

import com.bevelop.devbevelop.domain.project.domain.Responses;
import com.bevelop.devbevelop.domain.project.service.ProjectResponseService;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = {"3. Project Controller"})
@RequestMapping("/project")
public class ProjectLikeController {
    private final ProjectResponseService likeService;
    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "프로젝트 공감 누르기")
    @PostMapping("/response/{id}")
    public CommonResult addLike(@PathVariable Long id, @RequestBody Responses response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User signedInUser =  userService.findBySocialIdOrEmail(authentication.getName()).orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return likeService.addResponse(signedInUser, id, response)?
                responseService.getSuccessResult() : responseService.getFailResult(400, "Could not press like");
    }
}
