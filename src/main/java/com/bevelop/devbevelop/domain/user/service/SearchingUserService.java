package com.bevelop.devbevelop.domain.user.service;


import com.bevelop.devbevelop.domain.user.query.SearchingTags;
import com.bevelop.devbevelop.domain.user.query.UserStackDao;
import com.bevelop.devbevelop.domain.user.query.UserSummaryDao;
import com.bevelop.devbevelop.domain.user.query.data.UserStackData;
import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import com.bevelop.devbevelop.domain.user.service.response.UsersResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchingUserService {

    private final UserSummaryDao userSummaryDao;
    private final UserStackDao userStackDao;


    public SearchingUserService(
            final UserSummaryDao userSummaryDao,
            final UserStackDao userStackDao
    ) {
        this.userSummaryDao = userSummaryDao;
        this.userStackDao = userStackDao;
    }

    public UsersResponse getUsers(final SearchingTags searchingTags, final Pageable pageable) {
        final Slice<UserSummaryData> userData = userSummaryDao.searchBy(searchingTags, pageable);

        final List<Long> userIds = userData.getContent().stream()
                .map(UserSummaryData::getId)
                .collect(Collectors.toList());

        final Map<Long, List<UserStackData>> userStacks = userStackDao.findStacksByUserIds(userIds);


        return new UsersResponse(userData.getContent(), userStacks, userData.hasNext());
    }
}
