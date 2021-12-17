package com.ohou.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohou.backend.dto.review.CommentResponseDto;
import com.ohou.backend.dto.product.OptionResponseDto;
import com.ohou.backend.dto.product.ProductResponseDto;
import com.ohou.backend.entity.CategoryEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("상품 상세 페이지")
    void test() throws JsonProcessingException {
        //given
        List<String> colorDetail = new ArrayList<>();
        colorDetail.add("빨강");
        colorDetail.add("파랑");
        colorDetail.add("노랑");
        colorDetail.add("초록");
        colorDetail.add("보라");

        List<String> sizeDetail = new ArrayList<>();
        colorDetail.add("S");
        colorDetail.add("M");
        colorDetail.add("L");
        colorDetail.add("XL");

        OptionResponseDto optionColorResponseDto = OptionResponseDto.builder()
                .optionName("컬러")
                .detail(colorDetail)
                .build();
        OptionResponseDto optionSizeResponseDto = OptionResponseDto.builder()
                .optionName("사이즈")
                .detail(sizeDetail)
                .build();
        List<OptionResponseDto> optionResponseDtos = new ArrayList<>();
        optionResponseDtos.add(optionColorResponseDto);
        optionResponseDtos.add(optionSizeResponseDto);

        List<String> images = new ArrayList<>();
        images.add("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/163644725226111272.jpg");
        images.add("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/163644727849786741.jpg");
        images.add("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/163644728607605098.jpg");
        images.add("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/images/163644726742849223.jpg");

        List<String> details = new ArrayList<>();
        List<CommentResponseDto> comments = new ArrayList<>();
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(11L)
                .brandName("코코도르")
                .title("크리스마스 디퓨저 120ml 1+1+1")
                .categoryName(CategoryEnum.CHRISTMAS)
                .reviewCount(0)
                .discountRate(73)
                .price(13900)
                .option(optionResponseDtos)
                .images(images)
                .details(details)
                .comments(comments)
                .build();

        //when
        ResponseEntity<ProductResponseDto> response = restTemplate.getForEntity(
                "/api/products/" + 11,
                ProductResponseDto.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ProductResponseDto responseDto = response.getBody();
        assertNotNull(responseDto);

        ProductResponseDto productResponse = response.getBody();
        assertNotNull(productResponse);
        assertNotNull(productResponse.getId());
        assertEquals(productResponse.getTitle(), productResponseDto.getTitle());
        assertEquals(productResponse.getBrandName(), productResponseDto.getBrandName());
        assertEquals(productResponse.getCategoryName(), productResponseDto.getCategoryName());
        assertEquals(productResponse.getReviewCount(), productResponseDto.getReviewCount());
        assertEquals(productResponse.getDiscountRate(), productResponseDto.getDiscountRate());
        assertEquals(productResponse.getPrice(), productResponseDto.getPrice());
        assertTrue(productResponse.getImages().containsAll(productResponseDto.getImages()));
        assertEquals(productResponse.getDetails(), productResponseDto.getDetails());
        assertEquals(productResponse.getComments(), productResponseDto.getComments());
        //assertTrue(productResponse.getOption().containsAll(productResponseDto.getOption()));
    }
}

