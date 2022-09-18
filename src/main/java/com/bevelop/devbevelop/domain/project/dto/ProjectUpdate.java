package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
//import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.user.domain.User;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
//@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public class ProjectUpdate {
    @ApiParam(value = "프로젝트 명")
    private String title;

    @ApiParam(value = "프로젝트 템플릿")
    private ProjectTemplate projectTemplate;

    @ApiParam(value = "프로젝트 디테일")
    private String projectDetail;

    @ApiParam(value = "유저 정보")
    private User user;

//    @ApiParam(value = "팀 정보")
//    private Team team;

    @ApiParam(value = "project_teammates")
    private List<User> teammates;

    @ApiParam(value = "teammates_requests")
    private List<User> teammates_requests;
}
