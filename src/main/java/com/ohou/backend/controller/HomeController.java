package com.ohou.backend.controller;

import com.ohou.backend.dto.home.ProductListResponseDto;
import com.ohou.backend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/main/products")
    public ResponseEntity<List<ProductListResponseDto>> product(@RequestParam int page,
                                                                @RequestParam int size){

        List<ProductListResponseDto> productResponseDtoList = homeService.getProducts(page, size);
        return ResponseEntity.ok()
                .body(productResponseDtoList);
    }

    @GetMapping("/main/categorys")
    public ResponseEntity<List<ProductListResponseDto>> productFilteredByCategory(@RequestParam String category,
                                                                                  @RequestParam int page,
                                                                                  @RequestParam int size){

        List<ProductListResponseDto> productResponseDtoList = homeService.getProductFilteredByCategory(category, page, size);
        return ResponseEntity.ok()
                .body(productResponseDtoList);
    }


}
