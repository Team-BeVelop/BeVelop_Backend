package com.bevelop.devbevelop.domain.user.domain;

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
	private String socialId;

	@Column
	@NotNull
	private String email;

	@Column
	@NotNull
	private String password;

	@Column
	@NotNull
	private String nickname;

	private Role role;

	private String job;

	private String interests;

	private AttachedStacks attachedStacks;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id")
//    private Set<Stack> stacks = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "stack_list", joinColumns = @JoinColumn(name = "user_id"))
//    private Set<Stack> stackSet = new HashSet<>();

	private String provider;

	@Builder
	public User(String email, String password, String nickname, Role role, String job, String interests,
			AttachedStacks attachedStacks, String provider) {
		super();
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.role = role;
		this.job = job;
		this.interests = interests;
		this.attachedStacks = attachedStacks;
		this.provider = provider;
	}

	public User hashPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
		return this;
	}

	public User hashProvider(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.provider);
		return this;
	}

	public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
		return passwordEncoder.matches(checkPassword, getPassword());
	}

}
