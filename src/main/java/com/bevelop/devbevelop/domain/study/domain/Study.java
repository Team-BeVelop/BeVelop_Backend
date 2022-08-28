package com.bevelop.devbevelop.domain.study.domain;

import com.bevelop.devbevelop.domain.model.Division;
import com.bevelop.devbevelop.global.error.ErrorCode;
import com.bevelop.devbevelop.global.error.exception.BaseException;
import com.bevelop.devbevelop.global.error.exception.CustomException;
import com.bevelop.devbevelop.global.error.exception.FailureParticipationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Content content;

    @Embedded
    private Participants participants;

    @Embedded
    private RecruitPlanner recruitPlanner;


    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Study (
            final Content content, final Participants participants, final RecruitPlanner recruitPlanner,
            LocalDateTime createdAt
    ) {
        this(null, content, participants, recruitPlanner, createdAt);
    }

    public Study (
            final Long id, final Content content, final Participants participants, final RecruitPlanner recruitPlanner,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.content = content;
        this.participants = participants;
        this.recruitPlanner = recruitPlanner;
        this.createdAt = createdAt;
    }

    public boolean isParticipant(final Long userId) {
        return participants.isParticipation(userId);
    }

    public void changeStatus(final LocalDate now) {
        recruitPlanner.updateRecruiting(now);
    }

    public void leave(final Participant participant) {
        verifyCanLeave(participant);
        participants.leave(participant);
    }

    private void verifyCanLeave(final Participant participant) {
        if (participants.isOwner(participant.getUserId())) {
            throw new CustomException(ErrorCode.OWNER_CANT_LEAVE);
        }
        if (!participants.isParticipation(participant.getUserId())) {
            throw new CustomException(ErrorCode.USER_NOT_IN_STUDY);
        }
    }

    public void participate(final Long memberId) {
        if (recruitPlanner.isCloseEnrollment()) {
            throw new FailureParticipationException();
        }

        final Participant participant = new Participant(memberId);
        participants.participate(participant.getUserId());

        if (isFullOfCapacity()) {
            recruitPlanner.closeRecruiting();
        }
    }

    private boolean isFullOfCapacity() {
        return recruitPlanner.hasCapacity() && recruitPlanner.getCapacity() == participants.getSize();
    }

    public UserRole gerRole(final Long userId) {
        if (participants.isOwner(userId)) {
            return UserRole.OWNER;
        }
        if(participants.isParticipation(userId)) {
            return UserRole.USER;
        }
        return UserRole.NON_USER;
    }

    public void update(Long userId, Content content, RecruitPlanner recruitPlanner) {
        checkOwner(userId);
        this.content = content;
        this.recruitPlanner = recruitPlanner;
    }

    private void checkOwner(Long userId) {
        if (!participants.isOwner(userId)) {
            throw new CustomException(ErrorCode.OWNER_AUTH);
        }
    }



}