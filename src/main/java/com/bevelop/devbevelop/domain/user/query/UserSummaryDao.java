package com.bevelop.devbevelop.domain.user.query;

import com.bevelop.devbevelop.domain.tag.domain.UserCategoryName;
import com.bevelop.devbevelop.domain.user.query.data.UserSummaryData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class UserSummaryDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<UserSummaryData> USER_ROW_MAPPER = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("user_id");
        final String nickname = resultSet.getString("nickname");
        final String job = resultSet.getString("job");
        final String interests = resultSet.getString("interests");

        return new UserSummaryData(id, nickname, job, interests);
    };

    public Slice<UserSummaryData> searchBy(final SearchingTags searchingTags, final Pageable pageable) {
        final List<UserSummaryData> data = jdbcTemplate
                .query(sql(searchingTags), params(searchingTags, pageable), USER_ROW_MAPPER);

        return new SliceImpl<>(getCurrentPageUsers(data, pageable), pageable, hasNext(data, pageable));
    }

    private String sql(final SearchingTags searchingTags) {
        String result = "SELECT a.user_id, a.nickname, a.job, a.interests FROM bevelop_user as a "
                + "JOIN stack_tag ON a.user_id = stack_tag.user_id "
                + filtersInQueryClauseJob(searchingTags)
                + filtersInQueryClauseInterests(searchingTags)
                + filtersInQueryClauseStack(searchingTags)
                + "GROUP BY a.user_id "
                + "ORDER BY a.reg_time DESC "
                + "LIMIT :limit OFFSET :offset ";
        return result;
    }

    private String filtersInQueryClauseJob(final SearchingTags searchingTags) {

        String sql = "AND a.job IN ('{}') ";

        String result = searchingTags.getTagIdsBy(UserCategoryName.JOB);

        if (result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private String filtersInQueryClauseInterests(final SearchingTags searchingTags) {

        String sql = "AND a.interests IN ('{}') ";

        String result = searchingTags.getTagIdsBy(UserCategoryName.INTERESTS);

        if (result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private String filtersInQueryClauseStack(final SearchingTags searchingTags) {

        String sql = "AND stack_tag.stack_id IN ('{}') ";

        String result = searchingTags.getTagIdsBy(UserCategoryName.STACK);

        if(result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private Map<String, Object> params(final SearchingTags searchingTags,
                                       final Pageable pageable) {

        final Map<String, Object> tagIds = Stream.of(UserCategoryName.values())
                .collect(Collectors.toMap(name -> name.name().toUpperCase(), searchingTags::getTagIdsBy));



        Map<String, Object> param = new HashMap<>();
        param.put("limit", pageable.getPageSize() + 1);
        param.put("offset", pageable.getOffset());
        param.putAll(tagIds);
        return param;
    }

    private List<UserSummaryData> getCurrentPageUsers(final List<UserSummaryData> users, final Pageable pageable) {
        if (hasNext(users, pageable)) {
            return users.subList(0, users.size()-1);
        }
        return users;
    }

    private boolean hasNext(final List<UserSummaryData> users, final Pageable pageable) {
        return users.size() > pageable.getPageSize();
    }

}
