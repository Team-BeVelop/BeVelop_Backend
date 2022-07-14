package com.bevelop.devbevelop.domain.project.domain;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.bevelop.devbevelop.domain.team.domain.Team;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "project")
@Setter @Getter @ToString
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
    @Column(name = "projecttmp", nullable = false)
    private ProjectTemplate projecttmp;

    @Column(name = "project_title", nullable = false)
    private String title;

    @Lob
    @Column(name = "project_detail")
    private String detail;
}
