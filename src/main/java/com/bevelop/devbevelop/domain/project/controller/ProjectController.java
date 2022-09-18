package com.bevelop.devbevelop.domain.project.controller;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.ProjectForm;
import com.bevelop.devbevelop.domain.project.dto.ProjectUpdate;
import com.bevelop.devbevelop.domain.project.service.ProjectService;
import com.bevelop.devbevelop.domain.user.domain.User;
//import com.bevelop.devbevelop.domain.user.dto.UserRes;
import com.bevelop.devbevelop.domain.user.service.UserServiceImpl;
import com.bevelop.devbevelop.global.common.response.ListResult;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = {"3. Project Controller"})
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "프로젝트 찾기", notes = "PathVariable로 주어진 title을 가진 프로젝트의 정보")
    @GetMapping("/view/{title}")
    public Project findProject(@PathVariable String title) throws CustomException {
        Project project = projectService.findByTitle(title)
                .orElseThrow(()->new CustomException(ErrorCode.PROJECT_NOT_FOUND));

        return project;
    }

//    @Transactional
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "새로운 프로젝트 생성")
    @PutMapping("/new")
    public Project createProject(@Valid @RequestBody ProjectForm projectFormDto) throws CustomException {
        Project project = projectFormDto.toEntity();
        return projectService.create(project);
    }
//    @ApiOperation(value = "프로젝트 수정")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
//    @PutMapping("/edit/{title}")
//    public Project updateProject(@PathVariable("title") String title, @Valid @RequestBody ProjectUpdate projectUpdateDto) {
//        Project project = projectService.updateProject(title, projectUpdateDto);
//        return project;
//    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "jwt", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
//    })
    @ApiOperation(value = "프로젝트 삭제")
    @DeleteMapping(value = "/delete/{title}")
    public void deleteProject(@PathVariable("title") String title) {
        projectService.deleteProject(title);
    }

}