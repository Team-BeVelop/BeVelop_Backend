package com.bevelop.devbevelop.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor
@Getter @Setter @ToString
public class ProjectTemplate {
    @Enumerated(EnumType.STRING)
    @Column(name = "team_division", nullable = false)
    private Division division;

    //by month?
    @Column(name = "team_period", nullable = false)
    private int period;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_task", nullable = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column (name = "team_category", nullable = false)
    private Category category;
}
