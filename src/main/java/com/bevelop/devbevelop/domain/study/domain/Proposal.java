package com.bevelop.devbevelop.domain.study.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Long id;

    private Long studyId;

    private Long proposerId;

    private Long suggestedId;

    @Column(name = "proposal_msg")
    private String message;

    public Proposal(
            final Long studyId, final Long proposerId, final Long suggestedId, final String message
    ) {
        this.studyId = studyId;
        this.proposerId = proposerId;
        this.suggestedId = suggestedId;
        this.message = message;
    }

}
