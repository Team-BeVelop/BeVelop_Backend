package com.bevelop.devbevelop.domain.team.domain;

import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "team")
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "team_title",length = 50)
    @NotNull
    private String title;

    @Lob
    @Column(name = "team_detail")
    @NotNull
    private String detail;

    @Embedded
    @Column(name = "projecttmp")
    @NotNull
    private ProjectTemplate projecttmp;

    @Builder
    public Team(String title, String detail, ProjectTemplate projecttmp) {
        this.title = title;
        this.detail = detail;
        this.projecttmp = projecttmp;
    }
}
