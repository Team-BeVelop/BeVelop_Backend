package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.query.StudyDetailsDao;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchingStudyService {

    private final StudyDetailsDao studyDetailsDao;

    public SearchingStudyService(
            final StudyDetailsDao studyDetailsDao
    ) {
        this.studyDetailsDao = studyDetailsDao;
    }

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        return new StudyDetailResponse(content);
    }
}
