package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.ParticipatingMemberData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StudyMemberDao {

    private static final RowMapper<ParticipatingMemberData> MEMBER_FULL_DATA_ROW_MAPPER = createMemberFullDataRowMapper();


    private final NamedParameterJdbcTemplate jdbcTemplate;


    public List<ParticipatingMemberData> findMembersByStudyId(final Long studyId) {
        final String sql = "SELECT bevelop_user.user_id, bevelop_user.name, study_member.participate_msg, study_member.participate_status "
                + "FROM bevelop_user JOIN study_member ON bevelop_user.user_id = study_member.user_id "
                + "JOIN study ON study_member.study_id = study.id "
                + "WHERE study_member.study_id = :id";

        return jdbcTemplate.query(sql, Map.of("id", studyId), MEMBER_FULL_DATA_ROW_MAPPER);
    }

    private static RowMapper<ParticipatingMemberData> createMemberFullDataRowMapper() {
        return (resultSet, resultNumber) -> {
            Long userId = resultSet.getLong("user_id");
            String username = resultSet.getString("name");
            String message = resultSet.getString("participate_msg");
            String status = resultSet.getString("participate_status");

            return new ParticipatingMemberData(userId, username, message, status);
        };
    }

}
