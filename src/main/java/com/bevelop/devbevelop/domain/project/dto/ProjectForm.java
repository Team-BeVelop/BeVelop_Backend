package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProjectForm {
    @ApiParam(value = "프로젝트 명", required = true)
    private String title;

    @ApiParam(value = "프로젝트 템플릿", required = true)
    private ProjectTemplate projectTemplate;

    @ApiParam(value = "프로젝트 디테일")
    private String projectDetail;

//    @ApiParam(value = "유저 정보", required = true)
//    private User user;
//
//    @ApiParam(value = "팀 정보", required = true)
//    private Team team;

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .projecttmp(projectTemplate)
                .detail(projectDetail)
//                .user(user)
//                .team(team)
                .build();
    }
}
