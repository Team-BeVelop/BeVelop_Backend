package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.domain.stack.Stack;
import com.bevelop.devbevelop.global.entity.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "bevelop_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Job job;

    @Enumerated(EnumType.STRING)
    private Interests interests;

    @OneToMany(mappedBy = "user")
    private List<Stack> stacks = new ArrayList<>();


    @Builder
    public User(String email, String password, String name, Job job, Interests interests) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.job = job;
        this.interests = interests;
        this.role = Role.SLAVE;
    }

    public User hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
}
