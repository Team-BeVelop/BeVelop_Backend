package com.bevelop.devbevelop.domain.user.query;

import com.bevelop.devbevelop.domain.user.query.data.UserStackData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserStackDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final RowMapper<UserStackData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("user_id");
        final String stackName = rs.getString("stack_id");

        return new UserStackData(id, stackName);
    };

    private static final ResultSetExtractor<Map<Long, List<UserStackData>>> USER_WITH_STACK_ROW_MAPPER = rs -> {
        final Map<Long, List<UserStackData>> result = new LinkedHashMap<>();

        while (rs.next()) {
            final Long userId = rs.getLong("user_id");

            if (!result.containsKey(userId)) {
                result.put(userId, new ArrayList<>());
            }

            final Long id = rs.getLong("user_id");
            final String stackName = rs.getString("stack_id");
            final UserStackData userStackData = new UserStackData(id, stackName);

            final List<UserStackData> findStackByUserId = result.get(userId);
            findStackByUserId.add(userStackData);
        }
        return result;
    };

    public List<UserStackData> findStackByUserId(Long userId) {
        String sql = "SELECT user_id, stack_id FROM stack_tag "
                + "WHERE user_id = :userId";
        return jdbcTemplate.query(sql, Map.of("userId", userId), ROW_MAPPER);
    }

    public Map<Long, List<UserStackData>> findStacksByUserIds(List<Long> userIds) {
        if(userIds.isEmpty()) {
            return new HashMap<>();
        }

        final String sql = "SELECT stack_tag.user_id, stack_tag.stack_id FROM stack_tag "
                + "JOIN bevelop_user ON bevelop_user.user_id = stack_tag.user_id "
                + "WHERE bevelop_user.user_id IN (:userId)";

        final MapSqlParameterSource params = new MapSqlParameterSource("userId", userIds);

        try {
            return jdbcTemplate.query(sql, params, USER_WITH_STACK_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Map.of();
        }
    }


}
