package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.query.RecruitJobDao;
import com.bevelop.devbevelop.domain.study.query.RelatedFieldDao;
import com.bevelop.devbevelop.domain.study.query.StudyDetailsDao;
import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SearchingStudyService {

    private final StudyDetailsDao studyDetailsDao;

    private final RelatedFieldDao relatedFieldDao;
    private final RecruitJobDao recruitJobDao;

    public SearchingStudyService(
            final StudyDetailsDao studyDetailsDao,
            final RelatedFieldDao relatedFieldDao,
            final RecruitJobDao recruitJobDao) {
        this.studyDetailsDao = studyDetailsDao;
        this.relatedFieldDao = relatedFieldDao;
        this.recruitJobDao = recruitJobDao;
    }

    public StudyDetailResponse getStudyDetails(final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        final List<RelatedFieldData> relatedFieldDataList = relatedFieldDao.findFieldByStudyId(studyId);

        final List<RecruitJobData> recruitJobDataList = recruitJobDao.findRecruitJobByStudyId(studyId);


        return new StudyDetailResponse(content, relatedFieldDataList, recruitJobDataList);
    }
}
