package com.ohou.backend.repository;

import com.ohou.backend.entity.Product;
import com.ohou.backend.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
    ProductImages findByProduct(Product product);
}
