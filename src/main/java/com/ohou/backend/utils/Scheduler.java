package com.ohou.backend.utils;

import com.ohou.backend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final HomeService homeService;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 0 * * *")
    public void todayDealRefresh() {
        System.out.println("Hotdeal Product Refresh ...");
        // 오늘의딜 제품 교체
        homeService.todayDealRefresh();
    }
}
