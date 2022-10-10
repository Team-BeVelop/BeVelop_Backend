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
    private Long user_id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project")
    private Long project_id;

    @Enumerated(EnumType.STRING)
    private Responses response;

    @Builder
    public ProjectResponse(Long user_id, Long project_id, Responses response) {
        this.user_id = user_id;
        this.project_id = project_id;
        this.response = response;
    }

}
