package com.ohou.backend.repository;

import com.ohou.backend.entity.OptionEntity;
import com.ohou.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
    List<OptionEntity> findAllByProduct(Product product);
}
