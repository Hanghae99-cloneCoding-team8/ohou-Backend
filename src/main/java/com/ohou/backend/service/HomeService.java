package com.ohou.backend.service;

import com.ohou.backend.dto.home.ProductListResponseDto;
import com.ohou.backend.entity.CategoryEnum;
import com.ohou.backend.entity.Product;
import com.ohou.backend.entity.ProductImages;
import com.ohou.backend.entity.TodayDeal;
import com.ohou.backend.repository.ProductImagesRepository;
import com.ohou.backend.repository.ProductRepository;
import com.ohou.backend.repository.TodayDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final ProductRepository productRepository;
    private final TodayDealRepository todayDealRepository;
    private final ProductImagesRepository productImagesRepository;

    public List<ProductListResponseDto> getProducts(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return productPageToProductResponseDtoList(productPage);
    }

    public List<ProductListResponseDto> getProductFilteredByCategory(String category, int page, int size) {
        Page<Product> productPage = productRepository.findAllByCategory(CategoryEnum.valueOf(category), PageRequest.of(page, size));
        return productPageToProductResponseDtoList(productPage);
    }

    private List<ProductListResponseDto> productPageToProductResponseDtoList(Page<Product> productPage) {
        if (productPage.hasContent()) {
            List<ProductListResponseDto> productResponseDtoList = new ArrayList<>();

            for (Product product : productPage.toList()) {
                ProductImages productImage = productImagesRepository.findByProduct(product);

                productResponseDtoList.add(ProductListResponseDto.builder()
                        .id(product.getId())
                        .brandName(product.getBrand())
                        .title(product.getTitle())
                        .price(product.getPrice())
                        .reviewCount(product.getComment().size())
                        .imgSrc(productImage!=null?
                                productImage.getImgSrc():
                                "")
                        .build());
            }

            return productResponseDtoList;
        }

        return null;
    }

    public void todayDealRefresh() {
        long productCount = productRepository.count();
        int index = (int) (Math.random() * productCount);

        Page<Product> productList = productRepository.findAll(PageRequest.of(index, 4));
        List<TodayDeal> todayDealList = productListToTodayDealList(productList);
        if (productList.hasContent()) {
            todayDealRepository.deleteAll();
            todayDealRepository.saveAll(todayDealList);
        }
    }

    private List<TodayDeal> productListToTodayDealList(Page<Product> productList) {
        List<TodayDeal> todayDealList = new ArrayList<>();

        for (Product product : productList) {
            todayDealList.add(TodayDeal.builder()
                                .product(product)
                                .build());
        }

        return todayDealList;
    }
}
