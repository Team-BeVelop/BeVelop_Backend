package com.bevelop.devbevelop.domain.project.controller;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.dto.ProjectForm;
import com.bevelop.devbevelop.domain.project.dto.ProjectRes;
import com.bevelop.devbevelop.domain.project.dto.ProjectUpdate;
import com.bevelop.devbevelop.domain.project.service.ProjectService;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;
    
    private User getCurrentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return userService.findByEmail(authentication.getName()).orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
    
    private boolean isTheOwner(Long projectId, User user) {
    	return user.getId() == projectService.findById(projectId).getUserId();
    }

    @ApiOperation(value = "프로젝트 찾기", notes = "PathVariable로 주어진 title을 가진 프로젝트의 정보")
    @GetMapping("/view/{title}")
    public ProjectRes findProject(@PathVariable String title) throws CustomException {
        Project project = projectService.findByTitle(title);
        return new ProjectRes(project);
    }

    @ApiOperation(value = "모든 프로젝트", notes = "모든 프로젝트의 리스트")
    @GetMapping("/projectList")
    public List<Project> showProjectList() {
        return projectService.findAll();
    }

    @Transactional
    @ApiOperation(value = "새로운 프로젝트 생성")
    @PutMapping("/new")
    public Project createProject(@Valid @RequestBody ProjectForm projectFormDto) throws CustomException {
        Project project = projectFormDto.toEntity();

        //현재 로그인 된 유저로 오너 유저 정보 받아와서 오너 설정
        User owner = getCurrentUser();
        project.setUserId(owner.getId());

        return projectService.save(project);
    }

    @ApiOperation(value = "기존 프로젝트 수정")
    @PutMapping("/edit/{id}")
    public Project updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectUpdate projectUpdateDto) {
        Project project = projectUpdateDto.toEntity();

        //현재 로그인 된 유저가 오너인지 확인
        User owner = getCurrentUser();
        if(!isTheOwner(id, owner)) throw new CustomException(ErrorCode.NOT_OWNER);

        project.setId(id);
        project.setUserId(owner.getId());
        return projectService.save(project);
    }


    @ApiOperation(value = "프로젝트 삭제")
    @DeleteMapping(value = "/delete/{id}")
    public void deleteProject(@PathVariable("id") Long id) {
        //현재 로그인 된 유저가 오너인지 확인
    	User owner = getCurrentUser();
        if(!isTheOwner(id, owner)) throw new CustomException(ErrorCode.NOT_OWNER);

        projectService.deleteProject(id);
    }

}