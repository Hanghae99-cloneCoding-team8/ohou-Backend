package com.ohou.backend.service;

import com.github.javafaker.Faker;
import com.ohou.backend.dto.CommentResponseDto;
import com.ohou.backend.dto.ReviewRequestDto;
import com.ohou.backend.entity.Comment;
import com.ohou.backend.entity.Product;
import com.ohou.backend.passwordEncryption.AES256Util;
import com.ohou.backend.repository.CommentRepository;
import com.ohou.backend.repository.ProductRepository;
import com.ohou.backend.timeconversion.TimeConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public List<CommentResponseDto> getAllReviews(Long productId) {
        List<Comment> allComments = commentRepository.findAllByProductId(productId);
        List<CommentResponseDto> allCommentDtos = new ArrayList<>();
        for (Comment comment : allComments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getNickname(),
                    comment.getContent(),
                    TimeConversion.timeConversion(comment.getModifiedAt())
            );
            allCommentDtos.add(commentResponseDto);
        }
        return allCommentDtos;
    }

    @Transactional
    public void writeReview(Long productId, ReviewRequestDto reviewRequestDto) throws UnsupportedEncodingException, GeneralSecurityException {

        Faker faker = new Faker();
        AES256Util aes256Util = new AES256Util();
        String enPassword = aes256Util.encrypt(reviewRequestDto.getPassword());
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("상품이 없습니다"));
        Comment comment = new Comment(
                faker.name().fullName(),
                enPassword,
                reviewRequestDto.getContent()
        );
        product.getComment().add(comment);
        comment.setProduct(product);
        //productRepository.save(product);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto) throws UnsupportedEncodingException, GeneralSecurityException {
        Comment comment = commentRepository.getById(reviewId);
        AES256Util aes256Util = new AES256Util();
        if (!aes256Util.decrypt(comment.getPassword()).equals(reviewRequestDto.getPassword()))
                throw new IllegalArgumentException("비밀번호가 틀립니다");
        comment.update(reviewRequestDto.getContent());
    }

    public void deleteReview(Long reviewId) {
        commentRepository.deleteById(reviewId);
    }
}
