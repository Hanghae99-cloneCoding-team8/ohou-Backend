package com.ohou.backend.repository;

import com.ohou.backend.entity.CategoryEnum;
import com.ohou.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(CategoryEnum category, Pageable pageable);
    Page<Product> findAllByIdNotIn(List<Long> productList, Pageable pageable);
}
