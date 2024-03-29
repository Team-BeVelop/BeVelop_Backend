package com.bevelop.devbevelop.domain.user.domain;

import com.bevelop.devbevelop.domain.auth.dto.UserUpdateDto;
import com.bevelop.devbevelop.global.entity.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "bevelop_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String nickname;

    private String introduce;

    private Role role;

    private String job;

    private String interests;

    private String url;

    @Embedded
    private AttachedStacks attachedStacks;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id")
//    private Set<Stack> stacks = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "stack_list", joinColumns = @JoinColumn(name = "user_id"))
//    private Set<Stack> stackSet = new HashSet<>();


    public void setAttachedStacks(AttachedStacks attachedStacks) {
        this.attachedStacks = attachedStacks;
    }


    public void update(String nickname, String introduce, String job, String interests, String url,
                       AttachedStacks attachedStacks) {
        this.nickname = nickname;
        this.introduce = introduce;
        this.job = job;
        this.interests = interests;
        this.url = url;
        this.attachedStacks = attachedStacks;
    }

    public void update(UserUpdateDto userUpdateDto) {
        this.nickname = userUpdateDto.getNickname();
        this.introduce = userUpdateDto.getIntroduce();
        this.job = userUpdateDto.getJob();
        this.interests = userUpdateDto.getInterests();
        this.url = userUpdateDto.getUrl();
        this.attachedStacks = userUpdateDto.mapToAttachedStacks();
    }

    public User hashPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }


    @Builder
    public User(Long id, String email, String password, String nickname, String introduce, Role role,
                String job, String interests, String url, AttachedStacks attachedStacks) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduce = introduce;
        this.role = role;
        this.job = job;
        this.interests = interests;
        this.url = url;
        this.attachedStacks = attachedStacks;
    }

//	@Builder
//	public User(String email, String socialId, String nickname, String provider, String password) {
//		this.email = email;
//		this.socialId = socialId;
//		this.nickname = nickname;
//		this.provider = provider;
//		this.password = password;
//	}

}
