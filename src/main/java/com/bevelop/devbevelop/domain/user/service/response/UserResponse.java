package com.bevelop.devbevelop.domain.user.service.response;

import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String nickname;
    private String job;
    private String interests;

    public UserResponse(UserSummaryData userSummaryData) {
        this(userSummaryData.getId(), userSummaryData.getNickname(), userSummaryData.getJob(), userSummaryData.getInterests());
    }

}
