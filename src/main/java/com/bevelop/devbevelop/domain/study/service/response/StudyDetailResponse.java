package com.bevelop.devbevelop.domain.study.service.response;

import com.bevelop.devbevelop.domain.study.query.data.ParticipatingMemberData;
import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.user.query.data.OwnerData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

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
    private boolean status;
    private List<RelatedFieldData> fieldList;
    private List<RecruitJobData> jobList;
    private List<ParticipatingMemberData> members;

    public StudyDetailResponse(final StudyDetailsData study,
                               final boolean status,
                               final List<RelatedFieldData> fieldList,
                               final List<RecruitJobData> jobList,
                               final List<ParticipatingMemberData> participants) {
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
        this.status = status;
        this.fieldList = fieldList;
        this.jobList = jobList;
        this.members = participants;
    }

    public StudyDetailResponse(final StudyDetailsData study,
                               final boolean status,
                               final List<RelatedFieldData> fieldList,
                               final List<RecruitJobData> jobList
    ) {
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
        this.status = status;
        this.fieldList = fieldList;
        this.jobList = jobList;
    }

}
