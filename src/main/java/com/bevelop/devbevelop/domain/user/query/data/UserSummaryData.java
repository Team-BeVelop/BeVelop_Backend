package com.bevelop.devbevelop.domain.user.query.data;

import com.bevelop.devbevelop.domain.user.domain.AttachedStacks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSummaryData {
    private Long id;
    private String nickname;
    private String job;
    private String interests;

}
