package com.ohou.backend.controller;

import com.ohou.backend.crawling.seleniumCrawling;
import com.ohou.backend.dto.CommentResponseDto;
import com.ohou.backend.dto.ReviewRequestDto;
import com.ohou.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{productId}/review") // 필요 없음 product 불러올떄 같이
    public List<CommentResponseDto> getReviews(@PathVariable Long productId) {
        return commentService.getAllReviews(productId);
    }

    @PostMapping("/{productId}/review")
    public void postReview(@PathVariable Long productId, @RequestBody ReviewRequestDto reviewRequestDto) throws GeneralSecurityException, UnsupportedEncodingException {
        commentService.writeReview(productId, reviewRequestDto);
    }

    @PutMapping("/review/{reviewId}") // 수정된부분
    public void updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto) throws GeneralSecurityException, UnsupportedEncodingException {
        commentService.updateReview(reviewId, reviewRequestDto);
    }

    @DeleteMapping("/review/{reviewId}") // 수정된 부분
    public void deleteReview(@PathVariable Long reviewId) {
        commentService.deleteReview(reviewId);
    }
}
