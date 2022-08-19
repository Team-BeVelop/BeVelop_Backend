package com.bevelop.devbevelop.domain.team.dto;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.team.domain.Team;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Lob;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TeamForm {
    @ApiParam(value = "팀 명", required = true)
    private String title;

    @ApiParam(value = "팀 설명")
    private String detail;

    @ApiParam(value = "팀 설정")
    private ProjectTemplate projecttmp;

    public Team toEntity() {
        return Team.builder()
                .title(title)
                .detail(detail)
                .projecttmp(projecttmp)
                .build();
    }
}
