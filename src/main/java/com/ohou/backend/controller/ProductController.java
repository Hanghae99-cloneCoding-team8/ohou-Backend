package com.ohou.backend.controller;

import com.ohou.backend.dto.product.ProductResponseDto;
import com.ohou.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //제품 상세 페이지
    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductInfo(@PathVariable Long productId){
        ProductResponseDto productResponseDto = productService.getProductInfo(productId);
        return ResponseEntity.ok()
                .body(productResponseDto);
    }
}
