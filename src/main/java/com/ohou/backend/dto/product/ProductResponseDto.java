package com.ohou.backend.dto.product;

import com.ohou.backend.dto.CommentResponseDto;
import com.ohou.backend.entity.Option;
import com.ohou.backend.entity.ProductDetailImages;
import com.ohou.backend.entity.ProductImages;
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
    private String brandName;
    private String title;
    private int reviewCount;
    private int price;
    private List<OptionResponseDto> option;
    private List<String> images;
    private List<String> details;
    private List<CommentResponseDto> comments;
}
