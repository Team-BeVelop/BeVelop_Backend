package com.bevelop.devbevelop.domain.study.query.data;

import com.bevelop.devbevelop.domain.model.Division;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class StudyDetailsData {

    private final Long id;
    private final String division;
    private final String title;
    private final String shortTitle;
    private final String emailUrl;
    private final String kakaoUrl;
    private final String description;
    private final String recruitmentStatus;
    private final Integer currentMemberCount;
    private final Integer maxMemberCount;
    private final LocalDate createdDate;
    private final LocalDate enrollmentEndDate;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public static StudyDetailsDataBuilder builder() { return new StudyDetailsDataBuilder();}
}
