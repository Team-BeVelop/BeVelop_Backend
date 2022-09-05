package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RecruitJobDao {

    public static final RowMapper<RecruitJobData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("study_id");
        final String jobName = rs.getString("job_name");

        return new RecruitJobData(id, jobName);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<RecruitJobData> findRecruitJobByStudyId(Long studyId) {
        String sql = "SELECT study_id, job_name FROM study_recruit_job "
                + "WHERE study_id = :studyId";
        return jdbcTemplate.query(sql, Map.of("studyId", studyId), ROW_MAPPER);
    }
}
