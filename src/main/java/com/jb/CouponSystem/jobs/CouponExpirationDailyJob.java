package com.jb.CouponSystem.jobs;

import com.jb.CouponSystem.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CouponExpirationDailyJob {

    @Autowired
    private CouponRepository couponRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24, initialDelay = 1000)
    public void clearExpiredCoupons() {
        couponRepository.deleteExpiredCoupons();
    }
}
