package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Like 하는 사용자
    @JoinColumn(name="following")
    @ManyToOne
    private User fromUser;

    // Like 당하는 사용자
    @JoinColumn(name="follower")
    @ManyToOne
    private User toUser;

    @Builder
    public UserLike(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
