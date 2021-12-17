package com.ohou.backend.dto.product;

import com.ohou.backend.dto.review.CommentResponseDto;
import com.ohou.backend.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String brandName;
    private String title;
    private CategoryEnum categoryName;
    private int reviewCount;
    private int discountRate;
    private int price;
    private List<OptionResponseDto> option;
    private List<String> images;
    private List<String> details;
    private List<CommentResponseDto> comments;
}
