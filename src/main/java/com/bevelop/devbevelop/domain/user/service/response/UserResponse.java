package com.bevelop.devbevelop.domain.user.service.response;

import com.bevelop.devbevelop.domain.user.query.data.UserStackData;
import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {

    private Long id;
    private String nickname;
    private String job;
    private String interests;
    private List<UserStackData> userStacks;

    public UserResponse(UserSummaryData userSummaryData,
                        List<UserStackData> userStackData) {
        this(userSummaryData.getId(), userSummaryData.getNickname(), userSummaryData.getJob(), userSummaryData.getInterests(), userStackData);
    }

}
