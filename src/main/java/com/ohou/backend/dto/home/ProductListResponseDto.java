package com.ohou.backend.dto.home;

import com.ohou.backend.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {
    private Long id;
    private String brandName;
    private CategoryEnum categoryName;
    private String title;
    private int discountRate;
    private int price;
    private int reviewCount;
    private String imgSrc;
}
