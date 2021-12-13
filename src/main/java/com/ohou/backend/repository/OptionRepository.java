package com.ohou.backend.repository;

import com.ohou.backend.entity.Option;
import com.ohou.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByProduct(Product product);
}
