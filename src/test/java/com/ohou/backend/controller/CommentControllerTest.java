package com.ohou.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohou.backend.dto.product.ProductResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Nested
    @DisplayName("댓글 작성")
    class PostComments {
        @Test
        @Order(1)
        @DisplayName("댓글1 작성")
        void test1() throws JsonProcessingException {

            //given
            ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                    .content("good good")
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(reviewRequestDto);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            long productId = 1;

            //when
            ResponseEntity<ReviewRequestDto> response = restTemplate.postForEntity(
                    "/api/products/" + productId + "/reviews",
                    request,
                    ReviewRequestDto.class);

            //then
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @Order(2)
        @DisplayName("댓글 수정")
        void test2() throws JsonProcessingException {
            //given
            ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                    .content("bad bad")
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(reviewRequestDto);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            long productId = 1;

            // 상품 정보에 있는 댓글 조회
            ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(
                    "/api/products/" + productId,
                    ProductResponseDto.class);


            assertEquals(HttpStatus.OK, response.getStatusCode());
            ProductResponseDto productResponseDto = response.getBody();
            assertNotNull(productResponseDto);
            assertNotNull(productResponseDto.getComments());
            // 위에 새로 입력된 댓글 ID 스캔
            long commentId = -1;
            for (com.ohou.backend.dto.review.CommentResponseDto comment : productResponseDto.getComments()) {
                if (comment.getContent().equals("good good")) {
                    commentId = comment.getCommentId();
                    break;
                }
            }
            assertNotEquals(-1, commentId);
            /* when */
            restTemplate.put(
                    "/api/products/reviews/" + commentId,
                    request);

            // 수정 확인
            response = restTemplate.getForEntity(
                    "/api/products/" + productId,
                    ProductResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            productResponseDto = response.getBody();
            assertNotNull(productResponseDto);
            assertNotNull(productResponseDto.getComments());
            // 위에 새로 입력된 댓글 ID 스캔
            String content = "";
            for (com.ohou.backend.dto.review.CommentResponseDto comment : productResponseDto.getComments()) {
                if (comment.getContent().equals("bad bad")) {
                    content = comment.getContent();
                    break;
                }
            }
            assertEquals("bad bad", content);
        }

        @Test
        @Order(2)
        @DisplayName("댓글 수정 - 비번 에러")
        void test3() throws JsonProcessingException {
            //given
            ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                    .content("so so")
                    .password("1224")
                    .build();

            String requestBody = mapper.writeValueAsString(reviewRequestDto);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            long productId = 1;

            // 상품 정보에 있는 댓글 조회
            ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(
                    "/api/products/" + productId,
                    ProductResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            ProductResponseDto productResponseDto = response.getBody();
            assertNotNull(productResponseDto);
            assertNotNull(productResponseDto.getComments());
            // 위에 새로 입력된 댓글 ID 스캔
            long commentId = -1;
            for (com.ohou.backend.dto.review.CommentResponseDto comment : productResponseDto.getComments()) {
                if (comment.getContent().equals("good good")) {
                    commentId = comment.getCommentId();
                    break;
                }
            }
            assertEquals(-1, commentId);

            /* when */
            try {
                restTemplate.put(
                        "/api/products/reviews/" + commentId,
                        request);
            } catch (Exception e) {
                assertEquals("비밀번호가 틀립니다", e.getMessage());
            }

        }

        @Test
        @Order(2)
        @DisplayName("댓글 삭제")
        void test4() throws JsonProcessingException {
            //given
            ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(reviewRequestDto);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            long productId = 1;

            // 상품 정보에 있는 댓글 조회
            ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(
                    "/api/products/" + productId,
                    ProductResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            ProductResponseDto productResponseDto = response.getBody();
            assertNotNull(productResponseDto);
            assertNotNull(productResponseDto.getComments());
            // 위에 새로 입력된 댓글 ID 스캔
            long commentId = -1;
            for (com.ohou.backend.dto.review.CommentResponseDto comment : productResponseDto.getComments()) {
                if (comment.getContent().equals("bad bad")) {
                    commentId = comment.getCommentId();
                    break;
                }
            }
            assertNotEquals(-1, commentId);
            // restTemplate delete가 request를 받지않아서 exchange로 받을수 잇도록 변경
            ResponseEntity<String> resp = restTemplate.exchange("/api/products/reviews/" + commentId, HttpMethod.DELETE, request, String.class);
            assertEquals(HttpStatus.OK, resp.getStatusCode());

            // 삭제 됬는지 확인
            response = restTemplate.getForEntity(
                    "/api/products/" + productId,
                    ProductResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            productResponseDto = response.getBody();
            assertNotNull(productResponseDto);
            assertNotNull(productResponseDto.getComments());
            // 댓글 ID 스캔
            long deletedId = commentId;
            for (com.ohou.backend.dto.review.CommentResponseDto comment : productResponseDto.getComments()) {
                if (comment.getCommentId() == deletedId) {
                    deletedId = -1;
                    break;
                }
            }
            // 댓글 삭제 확인
            assertNotEquals(-1, deletedId);
        }
    }

    @Getter
    @Setter
    @Builder
    static class CommentResponseDto {
        private long commentId;
        private String nickname;
        private String content;
        private String moment;
    }

    @Getter
    @Setter
    @Builder
    static class ReviewRequestDto {
        private String password;
        private String content;
    }

}