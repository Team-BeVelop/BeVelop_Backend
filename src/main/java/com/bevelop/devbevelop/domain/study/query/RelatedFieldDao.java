package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class RelatedFieldDao {

    public static final RowMapper<RelatedFieldData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("study_id");
        final String fieldName = rs.getString("field_name");

        return new RelatedFieldData(id, fieldName);
    };

    private static final ResultSetExtractor<Map<Long, List<RelatedFieldData>>> STUDY_WITH_FIELD_ROW_MAPPER = rs -> {
        final Map<Long, List<RelatedFieldData>> result = new LinkedHashMap<>();

        while (rs.next()) {
            final Long studyId = rs.getLong("study_id");

            if (!result.containsKey(studyId)) {
                result.put(studyId, new ArrayList<>());
            }

            final Long id = rs.getLong("study_id");
            final String fieldName = rs.getString("field_name");
            final RelatedFieldData relatedFieldData = new RelatedFieldData(id, fieldName);

            final List<RelatedFieldData> findFieldByStudyId = result.get(studyId);
            findFieldByStudyId.add(relatedFieldData);
        }
        return result;
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<RelatedFieldData> findFieldByStudyId(Long studyId) {
        String sql = "SELECT study_id, field_name FROM study_related_field "
                + "WHERE study_id = :studyId";
        return jdbcTemplate.query(sql, Map.of("studyId", studyId), ROW_MAPPER);
    }

    public Map<Long, List<RelatedFieldData>> findFieldsByStudyIds(List<Long> studyIds) {
        if(studyIds.isEmpty()) {
            return new HashMap<>();
        }

        final String sql = "SELECT study_id, field_name FROM study_related_field "
                + "JOIN study ON study_related_field.study_id = study.id "
                + "WHERE study.id IN (:studyId)";

        final MapSqlParameterSource params = new MapSqlParameterSource("studyId", studyIds);

        try {
            return jdbcTemplate.query(sql, params, STUDY_WITH_FIELD_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Map.of();
        }
    }

}
