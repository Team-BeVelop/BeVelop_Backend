package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.domain.*;
import com.bevelop.devbevelop.domain.study.dto.StudyRequest;
import com.bevelop.devbevelop.domain.study.repository.StudyRepository;
import com.bevelop.devbevelop.domain.user.controller.UserController;
import com.bevelop.devbevelop.domain.user.domain.RecruitJobs;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.util.DateTimeSystem;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class StudyService {

    private final StudyRepository studyRepository;
    private final UserController userController;

    private final DateTimeSystem dateTimeSystem;

    public StudyService(final StudyRepository studyRepository,
                        final UserController userController,
                        final DateTimeSystem dateTimeSystem
    ) {
        this.studyRepository = studyRepository;
        this.userController = userController;
        this.dateTimeSystem = dateTimeSystem;
    }

    public Study createStudy(final StudyRequest studyRequest) {
        final LocalDateTime createdAt = dateTimeSystem.now();
        User owner = userController.getCurrentUser();

        final Participants participants = studyRequest.mapToParticipants(owner.getId());
        final RecruitPlanner recruitPlanner = studyRequest.mapToRecruitPlan();

        final Content content = studyRequest.mapToContent();
        final RelatedFields relatedFields = studyRequest.mapToRelatedFields();
        final RecruitJobs recruitJobs = studyRequest.mapToRecruitJobs();

        return studyRepository.save(
                new Study(content, relatedFields, recruitJobs, participants, recruitPlanner, createdAt)
        );
    }


    public void updateStudy(Long studyId, StudyRequest studyRequest) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        User owner = userController.getCurrentUser();

        study.update(owner.getId(), studyRequest.mapToContent(), studyRequest.mapToRecruitPlan(), studyRequest.mapToRelatedFields(), studyRequest.mapToRecruitJobs());
    }
}
