package com.ohou.backend.controller;

import com.ohou.backend.dto.review.ReviewRequestDto;
import com.ohou.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //리뷰 작성하기
    @PostMapping("/api/products/{productId}/reviews")
    public void postReview(@PathVariable Long productId, @RequestBody ReviewRequestDto reviewRequestDto) throws GeneralSecurityException, UnsupportedEncodingException {
        commentService.writeReview(productId, reviewRequestDto);
    }

    //리뷰 수정하기
    @PutMapping("/api/products/reviews/{reviewId}")
    public void updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto) throws GeneralSecurityException, UnsupportedEncodingException {
        commentService.updateReview(reviewId, reviewRequestDto);
    }

    //리뷰 삭제하기
    @DeleteMapping("/api/products/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto) throws GeneralSecurityException, UnsupportedEncodingException {
        commentService.deleteReview(reviewId, reviewRequestDto.getPassword());
    }
}
