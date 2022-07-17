package com.bevelop.devbevelop.domain.project.domain;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    @Column(name = "projecttmp")
    @NotNull
    private ProjectTemplate projecttmp;

    @Column(name = "project_title")
    @NotNull
    private String title;

    @Lob
    @Column(name = "project_detail")
    private String detail;
}
