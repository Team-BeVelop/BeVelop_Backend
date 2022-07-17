package com.bevelop.devbevelop.domain.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProjectTemplate {

    @Enumerated(EnumType.STRING)
    @Column(name = "team_division")
    @NotNull
    private Division division;

    //by month?
    @Column(name = "team_period")
    @NotNull
    private int period;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_task")
    @NotNull
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column (name = "team_category")
    @NotNull
    private Category category;
}
