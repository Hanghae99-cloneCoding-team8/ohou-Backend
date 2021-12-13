package com.ohou.backend.service;

import com.ohou.backend.dto.product.ProductResponseDto;
import com.ohou.backend.entity.Option;
import com.ohou.backend.entity.Product;
import com.ohou.backend.repository.OptionRepository;
import com.ohou.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;


    public ProductResponseDto getProductInfo(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new NullPointerException("유효하지 않은 상품입니다.");
        }

        // 하위항목이 추가로 존재하는 option은 fetch에서 모든 값을 가져올 수 없기 때문에 한 번 더 조회를 해줘야 함.
        List<Option> option = optionRepository.findAllByProduct(product.get());

        return ProductResponseDto.builder()
                .brandName(product.get().getBrand())
                .title(product.get().getTitle())
                .reviewCount(product.get().getComment().size())
                .price(product.get().getPrice())
                .option(option)
                .images(product.get().getProductImages())
                .details(product.get().getProductDetailImages())
                .build();
    }
}
