package com.bevelop.devbevelop.domain.study.dto;

import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.domain.study.domain.Content;
import com.bevelop.devbevelop.domain.study.domain.Participants;
import com.bevelop.devbevelop.domain.study.domain.RecruitPlanner;
import com.bevelop.devbevelop.domain.study.domain.RecruitStatus;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.bevelop.devbevelop.domain.study.domain.RecruitStatus.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StudyRequest {

    @ApiParam(value ="스터디 구분")
    private Division division;

    @NotBlank
    @ApiParam(value ="제목")
    private String title;

    @NotBlank
    @ApiParam(value ="짧은 소개")
    private String shortTitle;

    @NotBlank
    @ApiParam(value ="관련 링크")
    private String url;

    @NotBlank
    @ApiParam(value ="소개 글")
    private String description;

    @Min(1)
    @ApiParam(value ="모집 인원")
    private Integer maxMemberCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiParam(value ="모집 마감 날짜")
    private LocalDate enrollmentEndDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @ApiParam(value ="시작 예정일")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiParam(value ="마감 예정일")
    private LocalDate endDate;


    public Content mapToContent() { return new Content(division, title, shortTitle, url, description, startDate, endDate);}

    public Participants mapToParticipants(Long ownerId) {
        return Participants.createBy(ownerId);
    }

    public RecruitPlanner mapToRecruitPlan() {
        if (maxMemberCount != null && maxMemberCount == 1) {
            return new RecruitPlanner(maxMemberCount, RECRUITMENT_END, enrollmentEndDate);
        }

        return new RecruitPlanner(maxMemberCount, RECRUITMENT_START, enrollmentEndDate);
    }




}
