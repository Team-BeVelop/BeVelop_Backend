package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
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
    private RegisterRole registerRole;

    @Builder
    public UserRegister(User user, RegisterStatus status, RegisterRole registerRole) {
        this.user = user;
        this.status = status;
        this.registerRole = registerRole;
    }

}
