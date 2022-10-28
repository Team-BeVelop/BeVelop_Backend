package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.study.query.data.StudySummaryData;
import com.bevelop.devbevelop.domain.tag.domain.CategoryName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class StudySummaryDao {

    private static final RowMapper<StudySummaryData> STUDY_ROW_MAPPER = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final String division = resultSet.getString("division");
        final String title = resultSet.getString("title");
        final String shortTitle = resultSet.getString("short_title");

        return new StudySummaryData(id, division, title, shortTitle);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Slice<StudySummaryData> searchBy(final SearchingTags searchingTags, final Pageable pageable) {
        final List<StudySummaryData> data = jdbcTemplate
                .query(sql(searchingTags), params(searchingTags, pageable), STUDY_ROW_MAPPER);
        return new SliceImpl<>(getCurrentPageStudies(data, pageable), pageable, hasNext(data, pageable));
    }

    private String sql(final SearchingTags searchingTags) {
        String result = "SELECT study.id, study.division, study.title, study.short_title "
                + "FROM study "
                + "JOIN study_recruit_job ON study.id = study_recruit_job.study_id "
                + "JOIN study_related_field ON study.id = study_related_field.study_id "
                + filtersInQueryClauseDivision(searchingTags)
                + filtersInQueryClauseJob(searchingTags)
                + filtersInQueryClauseField(searchingTags)
                + "GROUP BY study.id "
                + "ORDER BY study.created_at DESC "
                + "LIMIT :limit OFFSET :offset ";
        return result;

    }

    private String filtersInQueryClauseDivision(final SearchingTags searchingTags) {

        String sql = "AND division IN ('{}') ";

        String result = searchingTags.getTagIdsBy(CategoryName.DIVISION);


        if (result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private String filtersInQueryClauseJob(final SearchingTags searchingTags) {

        String sql = "AND study_recruit_job.job_name IN ('{}') ";

        String result = searchingTags.getTagIdsBy(CategoryName.JOB);

        if (result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private String filtersInQueryClauseField(final SearchingTags searchingTags) {

        String sql = "AND study_related_field.field_name IN ('{}') ";

        String result = searchingTags.getTagIdsBy(CategoryName.FIELD);

        if (result.isEmpty()) {
            return "";
        } else {
            return sql.replaceAll("\\{\\}", result.toUpperCase());
        }
    }

    private Map<String, Object> params(final SearchingTags searchingTags,
                                       final Pageable pageable) {
        final Map<String, Object> tagIds = Stream.of(CategoryName.values())
                .collect(Collectors.toMap(name -> name.name().toUpperCase(), searchingTags::getTagIdsBy));

        Map<String, Object> param = new HashMap<>();
        param.put("limit", pageable.getPageSize() + 1);
        param.put("offset", pageable.getOffset());
        param.putAll(tagIds);
        System.out.println(param);
        return param;
    }

    private List<StudySummaryData> getCurrentPageStudies(final List<StudySummaryData> studies, final Pageable pageable) {
        if (hasNext(studies, pageable)) {
            return studies.subList(0, studies.size() - 1);
        }
        return studies;
    }

    private boolean hasNext(final List<StudySummaryData> studies, final Pageable pageable) {
        return studies.size() > pageable.getPageSize();
    }


}