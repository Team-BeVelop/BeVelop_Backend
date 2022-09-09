package com.bevelop.devbevelop.domain.study.domain;

import com.bevelop.devbevelop.global.error.exception.FailureParticipationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Participants {

    @Column(name = "current_member_count")
    private int size;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @ElementCollection
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private Set<Participant> participants = new HashSet<>();

    public Participants(Long ownerId, final Set<Long> participants) {
        this.size = participants.size() + 1;
        this.ownerId = ownerId;
        this.participants = participants.stream()
                .map(Participant::new)
                .collect(Collectors.toSet());
    }

    public static Participants createBy(Long ownerId) {
        return new Participants(ownerId, new HashSet<>());
    }

    void initParticipate(Long memberId, String message) {
        if (isParticipation(memberId)) {
            throw new FailureParticipationException();
        }
        participants.add(new Participant(memberId, ParticipateStatus.STAND_BY, message));
        size++;
    }

    public void leave(final Participant participant) {
        participants.remove(participant);
    }

    boolean isParticipation(final Long userId) {
        return participants.contains(new Participant(userId, ParticipateStatus.STAND_BY, "")) || isOwner(userId);
    }

    boolean isOwner(Long userId) { return ownerId.equals(userId);}

    public int getSize() {
        return size;
    }

//    private Set<Participant> getParticipants() {
//        Set<Participant> totalParticipants = new HashSet<>();
//        totalParticipants.add(new Participant(ownerId, ParticipateStatus.ACCEPT));
//        totalParticipants.addAll(this.participants);
//        return totalParticipants;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participants that = (Participants) o;
        return size == that.size && Objects.equals(ownerId, that.ownerId) && Objects.equals(participants, that.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, ownerId, participants);
    }

}
