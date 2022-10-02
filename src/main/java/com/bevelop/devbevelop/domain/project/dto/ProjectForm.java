package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.Project;
//import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.project.domain.Technique;
import com.bevelop.devbevelop.domain.project.domain.Website;
import io.swagger.annotations.ApiParam;
import lombok.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ProjectForm {
    @ApiParam(value = "프로젝트 명", required = true)
    private String title;

    @ApiParam(value = "기간")
    private int period;

    @ApiParam(value = "기술스택")
    private EnumSet<Technique> techniques;

    @ApiParam(value = "연관분야")
    private String category;

    @ApiParam(value = "웹사이트 목록")
    private Set<Website> sites;

    @ApiParam(value = "프로젝트 디테일")
    private String projectDetail;

    public Project toEntity() {
        return Project.builder()
                .title(title)
                .period(period)
                .techniques(techniques)
                .category(category)
                .sites(sites)
                .detail(projectDetail)
                .build();
    }
}
