package com.bevelop.devbevelop.domain.project.domain;

//import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

//    @JoinColumn(name = "owner_id")
    private Long userId;

    @Column(name = "project_title")
    @NotNull
    private String title;

    @Lob
    @Column(name = "project_detail")
    private String detail;

    @Column(name = "period")
    @NotNull
    private int period;

    //기술스택
    @Convert(converter = SetTechniqueConverter.class)
    @Column(name = "techniques")
    @NotNull
    private EnumSet<Technique> techniques;

    //연관분야
    @NotNull
    private String category;

    @ElementCollection
    private Set<Website> sites;

    @OneToMany(targetEntity=ProjectResponse.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProjectResponse> responses;

    @OneToMany(targetEntity=Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @NotNull
    public String email;

    @NotNull
    public String kakaoLink;

    @NotNull
    public String briefIntro;

    @Builder
    public Project(Long userId, String title, String detail, int period, EnumSet<Technique> techniques, String category, Set<Website> sites, String email, String kakaoLink, String briefIntro) {
        this.userId = userId;
        this.title = title;
        this.detail = detail;
        this.period = period;
        this.techniques = techniques;
        this.category = category;
        this.sites = sites;
        this.email = email;
        this.kakaoLink = kakaoLink;
        this.briefIntro = briefIntro;


        responses = new ArrayList<>();
        comments = new ArrayList<>();
    }
}
