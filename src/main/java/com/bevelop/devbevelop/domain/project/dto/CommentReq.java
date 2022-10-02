package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import com.bevelop.devbevelop.domain.project.domain.Project;
import com.bevelop.devbevelop.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReq {
    private String comment;

    /* Dto -> Entity */
    public Comment toEntity() {
        Comment comments = Comment.builder()
                .comment(comment)
                .build();
        return comments;
    }
}
