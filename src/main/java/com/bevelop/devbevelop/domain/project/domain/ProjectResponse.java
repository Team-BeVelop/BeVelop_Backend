package com.bevelop.devbevelop.domain.project.domain;

import com.bevelop.devbevelop.global.entity.BaseEntity;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProjectResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project")
    private Project project;

    @Enumerated(EnumType.STRING)
    private Responses response;

    @Builder
    public ProjectResponse(User user, Project project, Responses response) {
        this.user = user;
        this.project = project;
        this.response = response;
    }

}
