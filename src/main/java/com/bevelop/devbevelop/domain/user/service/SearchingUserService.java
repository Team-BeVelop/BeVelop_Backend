package com.bevelop.devbevelop.domain.user.service;


import com.bevelop.devbevelop.domain.user.query.SearchingTags;
import com.bevelop.devbevelop.domain.user.query.UserSummaryDao;
import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import com.bevelop.devbevelop.domain.user.service.response.UsersResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchingUserService {

    private final UserSummaryDao userSummaryDao;


    public SearchingUserService(
            final UserSummaryDao userSummaryDao
    ) {
        this.userSummaryDao = userSummaryDao;
    }

    public UsersResponse getUsers(final SearchingTags searchingTags, final Pageable pageable) {
        final Slice<UserSummaryData> userData = userSummaryDao.searchBy(searchingTags, pageable);
        System.out.println(userData.getContent());

        final List<Long> userIds = userData.getContent().stream()
                .map(UserSummaryData::getId)
                .collect(Collectors.toList());

        return new UsersResponse(userData.getContent(), userData.hasNext());
    }
}
