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

    public List<ProductListResponseDto> getTodayDeal() {
        List<TodayDeal> todayDealList = todayDealRepository.findAll();
        return todayDealListToProductResponseDtoList(todayDealList);
    }

    private List<ProductListResponseDto> todayDealListToProductResponseDtoList(List<TodayDeal> todayDealList) {
        if(todayDealList.size() > 0){
            List<ProductListResponseDto> productResponseDtoList = new ArrayList<>();

            for (TodayDeal todayDeal : todayDealList) {
                Product product = todayDeal.getProduct();
                productResponseDtoList.add(generateProductResponseDto(product));
            }

            return productResponseDtoList;
        }

        return null;
    }

    private List<ProductListResponseDto> productPageToProductResponseDtoList(Page<Product> productPage) {
        if (productPage.hasContent()) {
            List<ProductListResponseDto> productResponseDtoList = new ArrayList<>();

            for (Product product : productPage.toList()) {
                productResponseDtoList.add(generateProductResponseDto(product));
            }

            return productResponseDtoList;
        }

        return null;
    }

    private ProductListResponseDto generateProductResponseDto(Product product) {
        Page<ProductImages> productImage = productImagesRepository.findByProduct(product, PageRequest.of(0,1));
        return ProductListResponseDto.builder()
                .id(product.getId())
                .brandName(product.getBrand())
                .categoryName(product.getCategory())
                .title(product.getTitle())
                .discountRate(product.getDiscountRate())
                .price(product.getPrice())
                .reviewCount(product.getComment().size())
                .imgSrc(productImage.hasContent() ? productImage.getContent().get(0).getImgSrc() : "")
                .build();
    }

    public void todayDealRefresh() {
        long productCount = productRepository.count();
        int index = (int) (Math.random() * productCount);

        ////////////// 개선사항. QueryDSL 사용 예정.
        List<Long> alreadyProduct = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        Page<Product> productPage = productRepository.findAll(PageRequest.of(index, 1));
        if(productPage.hasContent()){
            productList.add(productPage.getContent().get(0));
            alreadyProduct.add(productPage.getContent().get(0).getId());
        }
        for(int i = 0; i < 3; i++){
            index = (int) (Math.random() * --productCount);
            productPage = productRepository.findAllByIdNotIn(alreadyProduct, PageRequest.of(index, 1));
            productList.add(productPage.getContent().get(0));
            alreadyProduct.add(productPage.getContent().get(0).getId());
        }
        ////////////// 개선사항 끝.

        List<TodayDeal> todayDealList = productListToTodayDealList(productList);
        todayDealRepository.deleteAll();
        todayDealRepository.saveAll(todayDealList);
    }

    private List<TodayDeal> productListToTodayDealList(List<Product> productList) {
        List<TodayDeal> todayDealList = new ArrayList<>();

        for (Product product : productList) {
            todayDealList.add(TodayDeal.builder()
                    .product(product)
                    .build());
        }

        return todayDealList;
    }
}
