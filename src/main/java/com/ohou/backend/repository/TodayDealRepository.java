package com.ohou.backend.repository;

import com.ohou.backend.entity.TodayDeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayDealRepository extends JpaRepository<TodayDeal, Long> {
}
