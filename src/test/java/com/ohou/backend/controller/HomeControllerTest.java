package com.ohou.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohou.backend.dto.home.ProductListResponseDto;
import com.ohou.backend.entity.CategoryEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HomeControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final int GET_PRODUCT_PAGE = 0;
    private final int GET_PRODUCT_SIZE = 10;
    private final int TODAY_DEAL_COUNT = 4;

    private List<ProductListResponseDto> productListResponseDtoList;

    @Test
    @Order(1)
    @DisplayName("상품 불러오기")
    void test1() throws JsonProcessingException {
        // given
        ProductListResponseDto compareDto = ProductListResponseDto.builder()
                .id(1L)
                .brandName("조아트")
                .categoryName(CategoryEnum.CHRISTMAS)
                .title("크리스마스트리 무장식트리 스카치 그린트리 3size")
                .discountRate(9)
                .price(18900)
                .reviewCount(1)
                .imgSrc("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/160385973659938111.jpg")
                .build();

        // when
        ResponseEntity<ProductListResponseDto[]> response = restTemplate.getForEntity(
                "/api/products?page=" + GET_PRODUCT_PAGE + "&size=" + GET_PRODUCT_SIZE,
                ProductListResponseDto[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ProductListResponseDto[] responseList = response.getBody();
        assertNotNull(responseList);
        assertTrue(GET_PRODUCT_SIZE >= responseList.length);

        // 상품 존재 여부 확인
        ProductListResponseDto productResponse = Arrays.stream(responseList)
                .filter(product -> compareDto.getId().equals(product.getId()))
                .findAny()
                .orElse(null);
        assertNotNull(productResponse);
        assertNotNull(productResponse.getId());
        assertEquals(productResponse.getTitle(), compareDto.getTitle());
        assertEquals(productResponse.getCategoryName(), compareDto.getCategoryName());
    }

    @Test
    @Order(2)
    @DisplayName("카테고리로 불러오기")
    void test2() throws JsonProcessingException {
        // given
        CategoryEnum categoryEnum = CategoryEnum.CHRISTMAS;
        ProductListResponseDto compareDto = ProductListResponseDto.builder()
                .id(1L)
                .brandName("조아트")
                .categoryName(CategoryEnum.CHRISTMAS)
                .title("크리스마스트리 무장식트리 스카치 그린트리 3size")
                .discountRate(9)
                .price(18900)
                .reviewCount(1)
                .imgSrc("https://image.ohou.se/i/bucketplace-v2-development/uploads/productions/160385973659938111.jpg")
                .build();

        // when
        ResponseEntity<ProductListResponseDto[]> response = restTemplate.getForEntity(
                "/api/products/categorys/" + categoryEnum + "?page=" + GET_PRODUCT_PAGE + "&size=" + GET_PRODUCT_SIZE,
                ProductListResponseDto[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ProductListResponseDto[] responseList = response.getBody();
        assertNotNull(responseList);
        assertTrue(GET_PRODUCT_SIZE >= responseList.length);

        // 상품 존재 여부 확인
        ProductListResponseDto productResponse = Arrays.stream(responseList)
                .filter(product -> compareDto.getId().equals(product.getId()))
                .findAny()
                .orElse(null);
        assertNotNull(productResponse);
        assertNotNull(productResponse.getId());
        assertEquals(productResponse.getTitle(), compareDto.getTitle());
        assertEquals(productResponse.getCategoryName(), compareDto.getCategoryName());
    }

    @Test
    @Order(3)
    @DisplayName("오늘의 딜 불러오기")
    void test3() throws JsonProcessingException {
        // given

        // when
        ResponseEntity<ProductListResponseDto[]> response = restTemplate.getForEntity(
                "/api/products/todayDeal",
                ProductListResponseDto[].class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ProductListResponseDto[] responseList = response.getBody();
        assertNotNull(responseList);
        assertTrue(TODAY_DEAL_COUNT >= responseList.length);
    }
}