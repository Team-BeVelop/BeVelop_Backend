package com.bevelop.devbevelop.domain.study.service.response;

import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.user.query.data.OwnerData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor
public class StudyDetailResponse {

    private Long id;
    private OwnerData owner;
    private String division;
    private String title;
    private String shortTitle;
    private String emailUrl;
    private String kakaoUrl;
    private String description;
    private String recruitmentStatus;
    private Integer currentMemberCount;
    private Integer maxMemberCount;
    private LocalDate createdDate;
    private LocalDate enrollmentEndDate;
    private LocalDate startDate;
    private LocalDate endDate;

    public StudyDetailResponse(final StudyDetailsData study) {
        this.id = study.getId();
        this.owner = study.getOwner();
        this.division = study.getDivision();
        this.title = study.getTitle();
        this.shortTitle = study.getShortTitle();
        this.emailUrl = study.getEmailUrl();
        this.kakaoUrl = study.getKakaoUrl();
        this.description = study.getDescription();
        this.recruitmentStatus = study.getRecruitmentStatus();
        this.currentMemberCount = study.getCurrentMemberCount();
        this.maxMemberCount = study.getMaxMemberCount();
        this.createdDate = study.getCreatedDate();
        this.enrollmentEndDate = study.getEnrollmentEndDate();
        this.startDate = study.getStartDate();
        this.endDate = study.getEndDate();
    }

}
