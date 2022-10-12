package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
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
public class RecruitJobDao {

    public static final RowMapper<RecruitJobData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("study_id");
        final String jobName = rs.getString("job_name");

        return new RecruitJobData(id, jobName);
    };

    private static final ResultSetExtractor<Map<Long, List<RecruitJobData>>> STUDY_WITH_JOB_ROW_MAPPER = rs -> {
        final Map<Long, List<RecruitJobData>> result = new LinkedHashMap<>();

        while (rs.next()) {
            final Long studyId = rs.getLong("study_id");

            if (!result.containsKey(studyId)) {
                result.put(studyId, new ArrayList<>());
            }

            final Long id = rs.getLong("study_id");
            final String jobName = rs.getString("job_name");
            final RecruitJobData recruitJobData = new RecruitJobData(id, jobName);

            final List<RecruitJobData> findRecruitJobByStudyId = result.get(studyId);
            findRecruitJobByStudyId.add(recruitJobData);
        }
        return result;
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<RecruitJobData> findRecruitJobByStudyId(Long studyId) {
        String sql = "SELECT study_id, job_name FROM study_recruit_job "
                + "WHERE study_id = :studyId";
        return jdbcTemplate.query(sql, Map.of("studyId", studyId), ROW_MAPPER);
    }

    public Map<Long, List<RecruitJobData>> findJobsByStudyIds(List<Long> studyIds) {
        if(studyIds.isEmpty()) {
            return new HashMap<>();
        }

        final String sql = "SELECT study_id, job_name FROM study_recruit_job "
                +"JOIN study ON study_recruit_job.study_id = study.id "
                + "WHERE study.id IN (:studyId)";
        final MapSqlParameterSource params = new MapSqlParameterSource("studyId", studyIds);

        try {
            return jdbcTemplate.query(sql, params, STUDY_WITH_JOB_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Map.of();
        }
    }
}
