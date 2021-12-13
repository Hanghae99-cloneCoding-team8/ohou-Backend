package com.ohou.backend.dto.product;

import com.ohou.backend.entity.Option;
import com.ohou.backend.entity.ProductDetailImages;
import com.ohou.backend.entity.ProductImages;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ProductResponseDto {
    private String brandName;
    private String title;
    private int reviewCount;
    private int price;
    private List<Option> option;
    private List<ProductImages> images;
    private List<ProductDetailImages> details;
}
