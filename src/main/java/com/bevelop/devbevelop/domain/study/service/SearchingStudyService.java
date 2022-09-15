package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.query.RecruitJobDao;
import com.bevelop.devbevelop.domain.study.query.RelatedFieldDao;
import com.bevelop.devbevelop.domain.study.query.StudyDetailsDao;
import com.bevelop.devbevelop.domain.study.query.StudyMemberDao;
import com.bevelop.devbevelop.domain.study.query.data.ParticipatingMemberData;
import com.bevelop.devbevelop.domain.study.query.data.RecruitJobData;
import com.bevelop.devbevelop.domain.study.query.data.RelatedFieldData;
import com.bevelop.devbevelop.domain.study.query.data.StudyDetailsData;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SearchingStudyService {

    private final StudyDetailsDao studyDetailsDao;

    private final UserService userService;
    private final RelatedFieldDao relatedFieldDao;
    private final RecruitJobDao recruitJobDao;
    private final StudyMemberDao memberDao;

    public SearchingStudyService(
            final StudyDetailsDao studyDetailsDao,
            final UserService userService,
            final RelatedFieldDao relatedFieldDao,
            final RecruitJobDao recruitJobDao,
            final StudyMemberDao memberDao) {
        this.studyDetailsDao = studyDetailsDao;
        this.userService = userService;
        this.relatedFieldDao = relatedFieldDao;
        this.recruitJobDao = recruitJobDao;
        this.memberDao = memberDao;
    }

    public StudyDetailResponse getStudyDetails(final UserDetails userDetails, final Long studyId) {
        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        User owner = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        final List<RelatedFieldData> relatedFieldDataList = relatedFieldDao.findFieldByStudyId(studyId);

        final List<RecruitJobData> recruitJobDataList = recruitJobDao.findRecruitJobByStudyId(studyId);

        if (owner.getId() == content.getOwner().getUserId()) {
            final List<ParticipatingMemberData> participants = memberDao.findMembersByStudyId(studyId);
            return new StudyDetailResponse(content, relatedFieldDataList, recruitJobDataList, participants);
        } else {
            return new StudyDetailResponse(content, relatedFieldDataList, recruitJobDataList);
        }




    }
}
