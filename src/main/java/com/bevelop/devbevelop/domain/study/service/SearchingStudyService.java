package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.query.*;
import com.bevelop.devbevelop.domain.study.query.data.*;
import com.bevelop.devbevelop.domain.study.service.response.StudiesResponse;
import com.bevelop.devbevelop.domain.study.service.response.StudyDetailResponse;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchingStudyService {

    private final StudySummaryDao studySummaryDao;
    private final StudyDetailsDao studyDetailsDao;
    private final UserService userService;
    private final RelatedFieldDao relatedFieldDao;
    private final RecruitJobDao recruitJobDao;
    private final StudyMemberDao memberDao;

    public SearchingStudyService(
            final StudySummaryDao studySummaryDao,
            final StudyDetailsDao studyDetailsDao,
            final UserService userService,
            final RelatedFieldDao relatedFieldDao,
            final RecruitJobDao recruitJobDao,
            final StudyMemberDao memberDao) {
        this.studySummaryDao = studySummaryDao;
        this.studyDetailsDao = studyDetailsDao;
        this.userService = userService;
        this.relatedFieldDao = relatedFieldDao;
        this.recruitJobDao = recruitJobDao;
        this.memberDao = memberDao;
    }

    public StudiesResponse getStudies(final SearchingTags searchingTags, final Pageable pageable) {
        final Slice<StudySummaryData> studyData = studySummaryDao.searchBy(searchingTags, pageable);

        final List<Long> studyIds = studyData.getContent().stream()
                .map(StudySummaryData::getId)
                .collect(Collectors.toList());

        final Map<Long, List<RecruitJobData>> recruitJobs =  recruitJobDao.findJobsByStudyIds(studyIds);
        final Map<Long, List<RelatedFieldData>> relatedFields = relatedFieldDao.findFieldsByStudyIds(studyIds);


        return new StudiesResponse(studyData.getContent(), recruitJobs, relatedFields, studyData.hasNext());

    }



    public StudyDetailResponse getStudyDetails(final Long studyId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        final StudyDetailsData content = studyDetailsDao.findBy(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        User owner = userService.findByEmail(authentication.getName())
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