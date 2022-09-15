package com.bevelop.devbevelop.domain.study.service;

import com.bevelop.devbevelop.domain.study.domain.Participant;
import com.bevelop.devbevelop.domain.study.domain.ParticipateStatus;
import com.bevelop.devbevelop.domain.study.domain.Study;
import com.bevelop.devbevelop.domain.study.dto.ParticipateStudyDto;
import com.bevelop.devbevelop.domain.study.repository.StudyRepository;
import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.domain.user.repository.UserRepository;
import com.bevelop.devbevelop.domain.user.service.UserService;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class StudyParticipantService {

    private final UserService userService;

    private final StudyRepository studyRepository;


    public StudyParticipantService(final UserService userService,
                                   final StudyRepository studyRepository) {
        this.userService = userService;
        this.studyRepository = studyRepository;
    }
    public void participateStudy(UserDetails userDetails, final Long studyId, final ParticipateStudyDto message) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        study.initParticipate(user.getId(), message.getMessage());
    }

    public void leaveStudy(UserDetails userDetails, Long studyId) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        final Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        study.leave(new Participant(user.getId(), ParticipateStatus.STAND_BY, ""));

    }

    public void acceptStudy(UserDetails userDetails, Long studyId, Long userId) {
        User owner = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        final Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        study.accept(new Participant(userId, ParticipateStatus.STAND_BY), userId, owner.getId());
    }


    public void refuseStudy(UserDetails userDetails, Long studyId, Long userId) {
        User owner = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        final Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        study.refuse(new Participant(userId, ParticipateStatus.REFUSE), userId, owner.getId());
    }
}
