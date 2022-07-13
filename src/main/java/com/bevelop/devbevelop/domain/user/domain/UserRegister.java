package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserRegister extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="register_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private RegisterStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UserRegister(User user, RegisterStatus status, Role role) {
        this.user = user;
        this.status = status;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRegister{" +
                "user=" + user +
                ", status='" + status + '\'' +
                ", role='" + role + '\'' +
                '}';
    }



}
