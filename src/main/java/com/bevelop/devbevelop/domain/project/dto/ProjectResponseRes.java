package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.ProjectResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseRes {
    private Long id;
    private Long userId;
    private Long projectId;
    private String response;


    /* Entity -> Dto*/
    public ProjectResponseRes(ProjectResponse projectResponse) {
        this.id = projectResponse.getId();
        this.userId = projectResponse.getUser().getId();
        this.projectId = projectResponse.getProject().getId();
        this.response = projectResponse.getResponse();
    }

}
