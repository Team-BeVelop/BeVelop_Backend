package com.bevelop.devbevelop.domain.study.service.response;

import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudySummaryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudyResponse {

    private Long id;
    private String division;
    private String title;
    private String shortTitle;
    private List<RecruitJobData> studyJobs;
    private List<RelatedFieldData> relatedFields;


    public StudyResponse(StudySummaryData studySummaryData,
                         List<RecruitJobData> studyJobs,
                         List<RelatedFieldData> relatedFields) {
        this(studySummaryData.getId(),  studySummaryData.getDivision(), studySummaryData.getTitle(), studySummaryData.getShortTitle(),
                studyJobs, relatedFields);
    }
}