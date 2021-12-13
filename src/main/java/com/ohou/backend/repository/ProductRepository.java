package com.ohou.backend.repository;

import com.ohou.backend.entity.CategoryEnum;
import com.ohou.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategory(CategoryEnum category, Pageable pageable);
}
