package com.ohou.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private long commentId;
    private String nickname;
    private String content;
    private String moment;
}
