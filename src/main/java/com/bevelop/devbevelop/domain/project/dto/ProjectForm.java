package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.ProjectTemplate;
import com.bevelop.devbevelop.domain.project.domain.Project;
//import com.bevelop.devbevelop.domain.team.domain.Team;
import io.swagger.annotations.ApiParam;
import lombok.*;

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


    public Project toEntity() {
        return Project.builder()
                .title(title)
                .projecttmp(projectTemplate)
                .detail(projectDetail)
                .build();
    }
}
