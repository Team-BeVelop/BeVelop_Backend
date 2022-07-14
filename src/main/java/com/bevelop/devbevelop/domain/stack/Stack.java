package com.bevelop.devbevelop.domain.stack;

import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name = "stacks")
@NoArgsConstructor
@Entity
public class Stack {

    @Id
    @GeneratedValue
    @Column(name="stack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private StackName stackName;
}
