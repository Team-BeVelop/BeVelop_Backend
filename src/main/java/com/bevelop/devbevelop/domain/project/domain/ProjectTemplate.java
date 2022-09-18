package com.bevelop.devbevelop.domain.project.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.EnumSet;
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProjectTemplate {

    @Enumerated(EnumType.STRING)
    @Column(name = "division")
    @NotNull
    private Division division;

    //by month?
    @Column(name = "period")
    @NotNull
    private int period;

    @Convert(converter = SetTaskConverter.class)
    @Column(name = "tasks")
    @NotNull
    private EnumSet<Task> tasks;

    @Enumerated(EnumType.STRING)
    @Column (name = "category")
    @NotNull
    private Category category;
}
