package com.bevelop.devbevelop.domain.user.service.response;

import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UsersResponse {

    private List<UserResponse> users;

    private boolean hasNext;

    public UsersResponse(
            final List<UserSummaryData> userSummaryData,
            final boolean hasNext
    ) {
        this.users = getUserResponse(userSummaryData);
        this.hasNext = hasNext;
    }

    private List<UserResponse> getUserResponse(
            final List<UserSummaryData> usersSummaryData
    ) {
        return usersSummaryData.stream()
                .map(userSummaryData -> new UserResponse(userSummaryData))
                .collect(Collectors.toList());
    }
}
