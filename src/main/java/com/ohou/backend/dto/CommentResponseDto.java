package com.ohou.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private String nickname;
    private String content;
    private String moment;

}
