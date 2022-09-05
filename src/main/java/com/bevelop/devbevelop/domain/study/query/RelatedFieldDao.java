package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RelatedFieldDao {

    public static final RowMapper<RelatedFieldData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("study_id");
        final String fieldName = rs.getString("field_name");

        return new RelatedFieldData(id, fieldName);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<RelatedFieldData> findFieldByStudyId(Long studyId) {
        String sql = "SELECT study_id, field_name FROM study_related_field "
                + "WHERE study_id = :studyId";
        return jdbcTemplate.query(sql, Map.of("studyId", studyId), ROW_MAPPER);
    }
}
