package com.bevelop.devbevelop.domain.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RecruitJob {

    @Column(name = "job_name", nullable = false)
    private String recruitJob;
}
