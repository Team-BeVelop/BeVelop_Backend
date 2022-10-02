package com.bevelop.devbevelop.domain.project.dto;

import com.bevelop.devbevelop.domain.project.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRes {
    private Long id;
    private String comment;
    private String userName;
    private String projectTitle;

    /* Entity -> Dto*/
    public CommentRes(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userName = comment.getUser().getName();
        this.projectTitle = comment.getProject().getTitle();
    }
}
