package com.bevelop.devbevelop.domain.team.domain;

import com.bevelop.devbevelop.domain.model.Category;
import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.domain.model.ProjectTemplate;
import com.bevelop.devbevelop.domain.model.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "team")
@Getter @Setter @ToString
public class Team {

    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "team_title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "team_detail", nullable = false)
    private String detail;

    @Embedded
    @Column(name = "projecttmp", nullable = false)
    private ProjectTemplate projecttmp;
}
