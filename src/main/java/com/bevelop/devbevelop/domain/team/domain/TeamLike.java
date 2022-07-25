package com.bevelop.devbevelop.domain.team.domain;

import com.bevelop.devbevelop.global.entity.BaseEntity;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class TeamLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    @Builder
    public TeamLike(User user, Team team) {
        this.user = user;
        this.team = team;
    }
}
