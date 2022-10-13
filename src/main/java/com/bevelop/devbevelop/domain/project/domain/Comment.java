package com.bevelop.devbevelop.domain.project.domain;

import com.bevelop.devbevelop.domain.user.domain.User;
import com.bevelop.devbevelop.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; // 댓글 내용

    @ManyToOne
//    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private User user; // 작성자
}
