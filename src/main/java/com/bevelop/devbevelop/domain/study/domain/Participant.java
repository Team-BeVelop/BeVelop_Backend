package com.bevelop.devbevelop.domain.study.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Participant {

    @Column(name ="user_id", nullable = false)
    private Long userId;

    @Column(updatable = false, nullable = false)
    private LocalDate participationDate;

    @Enumerated(EnumType.STRING)
    @Column(name= "participate_status", nullable = false)
    private ParticipateStatus status;

    @Column(name = "participate_msg", nullable = false)
    private String message;

    public Participant(final Long userId) {
        this.userId = userId;
        this.participationDate = LocalDate.now();
    }

    public Participant(final Long userId, final ParticipateStatus status) {
        this.userId = userId;
        this.participationDate = LocalDate.now();
        this.status = status;
    }

    public Participant(final Long userId, final ParticipateStatus status, final String message) {
        this.userId = userId;
        this.participationDate = LocalDate.now();
        this.status = status;
        this.message = message;
    }

    public void accept(final Participant participant) {
        participant.setStatus(ParticipateStatus.ACCEPT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(userId, that.userId) && Objects.equals(participationDate, that.participationDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, participationDate, status);
    }
}
