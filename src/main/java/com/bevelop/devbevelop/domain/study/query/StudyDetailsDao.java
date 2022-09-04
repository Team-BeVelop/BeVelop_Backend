package com.bevelop.devbevelop.domain.study.query;

import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsDataBuilder;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudyDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<StudyDetailsData> findBy(Long studyId) {
        try {
            String sql =
                    "SELECT study.id, division, title, short_title, email_url, kakao_url, description, recruitment_status, "
                    + "current_member_count, max_member_count, created_at, enrollment_end_date, start_date, end_date "
                    + "FROM study WHERE study.id = ?";

            final StudyDetailsData data = jdbcTemplate.query(sql, new StudyDetailsDataExtractor(), studyId);
            return Optional.of(data);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class StudyDetailsDataExtractor implements ResultSetExtractor<StudyDetailsData> {
        private final StudyDetailsDataBuilder builder;

        public StudyDetailsDataExtractor() { builder = StudyDetailsData.builder();}

        @Override
        public StudyDetailsData extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
            if (resultSet.next()) {
                appendStudyContent(resultSet);
                return builder.build();
            }
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        private void appendStudyContent(final ResultSet rs) throws SQLException {
            Long id = rs.getLong("id");
            String division = rs.getString("division");
            String title = rs.getString("title");
            String shortTitle = rs.getString("short_title");
            String emailUrl = rs.getString("email_url");
            String kakaoUrl = rs.getString("kakao_url");
            String description = rs.getString("description");
            String recruitmentStatus = rs.getString("recruitment_status");
            LocalDate createdDate = rs.getObject("created_at", LocalDate.class);
            LocalDate enrollmentEndDate = rs.getObject("enrollment_end_date", LocalDate.class);
            LocalDate startDate = rs.getObject("start_date", LocalDate.class);
            LocalDate endDate = rs.getObject("end_date", LocalDate.class);

            builder.id(id).division(division).title(title).shortTitle(shortTitle).emailUrl(emailUrl).kakaoUrl(kakaoUrl)
                    .description(description).recruitmentStatus(recruitmentStatus)
                    .createdDate(createdDate).enrollmentEndDate(enrollmentEndDate)
                    .startDate(startDate).endDate(endDate);
        }
    }


}
