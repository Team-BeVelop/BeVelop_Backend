package com.bevelop.devbevelop.domain.study.service.response;

import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudySummaryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class StudiesResponse {

    private List<StudyResponse> studies;

    private boolean hasNext;

    public StudiesResponse(
            final List<StudySummaryData> studySummaryData,
            final Map<Long, List<RecruitJobData>> recruitJobs,
            final Map<Long, List<RelatedFieldData>> relatedFields,
            final boolean hasNext
    ) {
        this.studies = getStudyResponse(studySummaryData, recruitJobs, relatedFields);
        this.hasNext = hasNext;
    }

    private List<StudyResponse> getStudyResponse(
            final List<StudySummaryData> studiesSummaryData,
            final Map<Long, List<RecruitJobData>> recruitJobs,
            final Map<Long, List<RelatedFieldData>> relatedFields
    ) {
        return studiesSummaryData.stream()
                .map(studySummaryData -> new StudyResponse(studySummaryData, recruitJobs.get(studySummaryData.getId()), relatedFields.get(studySummaryData.getId())))
                .collect(Collectors.toList());
    }

}