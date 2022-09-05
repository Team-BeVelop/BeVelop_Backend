package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.query.RelatedFieldDao;
import com.bevelop.devbevelop.domain.study.query.StudyDetailsDao;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SearchingStudyService {

    private final StudyDetailsDao studyDetailsDao;

    private final RelatedFieldDao relatedFieldDao;

    public SearchingStudyService(
            final StudyDetailsDao studyDetailsDao,
            RelatedFieldDao relatedFieldDao) {
        this.studyDetailsDao = studyDetailsDao;
        this.relatedFieldDao = relatedFieldDao;
    }

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        final List<RelatedFieldData> relatedFieldDataList = relatedFieldDao.findFieldByStudyId(studyId);


        return new StudyDetailResponse(content, relatedFieldDataList);
    }
}
