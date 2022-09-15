package com.bevelop.devbevelop.domain.study.query.data;

import com.bevelop.devbevelop.domain.study.domain.ParticipateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ParticipatingMemberData {

    @JsonProperty("id")
    private Long userId;
    private String username;
    private String message;
    private String status;

}
