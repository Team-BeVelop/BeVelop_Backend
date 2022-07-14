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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="user_like_uk",
                        columnNames = {"following", "follower"}
                )
        }
)
public class UserLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_like_id")
    private Long id;

    // Like 하는 사용자
    @JoinColumn(name="following")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    // Like 당하는 사용자
    @JoinColumn(name="follower")
    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;

    @Builder
    public UserLike(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
