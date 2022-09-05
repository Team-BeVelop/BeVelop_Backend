package com.bevelop.devbevelop.domain.study.query.data;

import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.domain.user.query.data.OwnerData;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class StudyDetailsDataBuilder {

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

    StudyDetailsDataBuilder() { }


    public StudyDetailsDataBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public StudyDetailsDataBuilder owner(OwnerData owner) {
        this.owner = owner;
        return this;
    }

    public StudyDetailsDataBuilder division(String division) {
        this.division = division;
        return this;
    }

    public StudyDetailsDataBuilder title(String title) {
        this.title = title;
        return this;
    }

    public StudyDetailsDataBuilder shortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public StudyDetailsDataBuilder emailUrl(String emailUrl) {
        this.emailUrl = emailUrl;
        return this;
    }

    public StudyDetailsDataBuilder kakaoUrl(String kakaoUrl) {
        this.kakaoUrl = kakaoUrl;
        return this;
    }

    public StudyDetailsDataBuilder description(String description) {
        this.description = description;
        return this;
    }

    public StudyDetailsDataBuilder recruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
        return this;
    }

    public StudyDetailsDataBuilder currentMemberCount(Integer currentMemberCount) {
        this.currentMemberCount = currentMemberCount;
        return this;
    }

    public StudyDetailsDataBuilder maxMemberCount(Integer maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
        return this;
    }

    public StudyDetailsDataBuilder createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public StudyDetailsDataBuilder enrollmentEndDate(LocalDate enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
        return this;
    }

    public StudyDetailsDataBuilder startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public StudyDetailsDataBuilder endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public StudyDetailsData build() {
        return new StudyDetailsData(
                id, owner, division, title, shortTitle, emailUrl, kakaoUrl, description, recruitmentStatus, currentMemberCount, maxMemberCount, createdDate, enrollmentEndDate,
                startDate, endDate
        );
    }






}
