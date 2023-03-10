package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.project.domain.Website;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRes {
    private String title;
    private Long userId;

    private int period;

    //기술스택
//    private List<String> techniques;

    //연관분야
    private String category;

    private List<Website> sites;

    private String detail;
    private List<ProjectResponseRes> likes;
    private List<CommentRes> comments;
    private String email;
    private String kakaoLink;
    private String briefIntro;

    /* Entity -> Dto*/
    public ProjectRes(Project project) {
        this.title = project.getTitle();
        this.userId = project.getUserId();
        this.period = project.getPeriod();
//        this.techniques = project.getTechniques();
        this.category = project.getCategory();
        this.sites = project.getSites();
        this.detail = project.getDetail();
        this.likes = project.getResponses().stream().map(ProjectResponseRes::new).collect(Collectors.toList());
        this.comments = project.getComments().stream().map(CommentRes::new).collect(Collectors.toList());
        this.email = project.getEmail();
        this.kakaoLink = project.getKakaoLink();
        this.briefIntro = project.getBriefIntro();
    }
}
