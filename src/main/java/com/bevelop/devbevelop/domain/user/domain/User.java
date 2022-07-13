package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.domain.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

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
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", job=" + job + '\'' +
                ", interests=" + interests + '\'' +
                '}';
    }

}
