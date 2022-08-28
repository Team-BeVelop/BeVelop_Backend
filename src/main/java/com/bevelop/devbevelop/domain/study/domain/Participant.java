package com.bevelop.devbevelop.domain.study.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Participant {

    @Column(name ="user_id", nullable = false)
    private Long userId;

    @Column(updatable = false, nullable = false)
    private LocalDate participationDate;

    public Participant(final Long userId) {
        this.userId = userId;
        this.participationDate = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(userId, that.userId) && Objects.equals(participationDate, that.participationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, participationDate);
    }
}
