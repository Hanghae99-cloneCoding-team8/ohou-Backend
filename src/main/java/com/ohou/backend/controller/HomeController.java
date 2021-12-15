package com.ohou.backend.controller;

import com.ohou.backend.dto.home.ProductListResponseDto;
import com.ohou.backend.entity.Product;
import com.ohou.backend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeController {
    private final HomeService homeService;

    //상품 불러오기
    @GetMapping("/api/products")
    public ResponseEntity<List<ProductListResponseDto>> product(@RequestParam int page,
                                                                @RequestParam int size){

        List<ProductListResponseDto> productResponseDtoList = homeService.getProducts(page, size);
        return ResponseEntity.ok()
                .body(productResponseDtoList);
    }

    //카테고리 필터
    @GetMapping("/api/products/categorys/{categoryName}")
    public ResponseEntity<List<ProductListResponseDto>> productFilteredByCategory(@PathVariable String categoryName,
                                                                                  @RequestParam int page,
                                                                                  @RequestParam int size){

        List<ProductListResponseDto> productResponseDtoList = homeService.getProductFilteredByCategory(categoryName, page, size);
        return ResponseEntity.ok()
                .body(productResponseDtoList);
    }

    //오늘의 딜 불러오기
    @GetMapping("/api/products/todayDeal")
    public ResponseEntity<List<ProductListResponseDto>> todayDeal(){
        List<ProductListResponseDto> productListResponseDtoList = homeService.getTodayDeal();
        return ResponseEntity.ok()
                .body(productListResponseDtoList);
    }
}
