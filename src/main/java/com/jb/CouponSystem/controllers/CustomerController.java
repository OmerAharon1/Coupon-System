package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.mapper.CouponMapper;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
//for authorization add , @RequestHeader("Authorization") UUID token

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private CouponMapper couponMapper;


    @PostMapping("purchase/{customerId}")
    void purchaseCoupon(@RequestHeader("Authorization") UUID uuid, @PathVariable int customerId, @RequestBody CouponDto couponDto) throws CouponSystemException {
        customerService.purchaseCoupon(customerId, couponDto, uuid);
    }

    @GetMapping("coupons/{customerId}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId) {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("coupons/{customerId}/{Category}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId, @PathVariable Category category) {
        return customerService.getCustomerCoupons(customerId, category);
    }

    @GetMapping("coupons/{customerId}/{maxPrice}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId, @RequestParam double maxPrice) {
        return customerService.getCustomerCoupons(customerId, maxPrice);
    }

    @GetMapping("customer/{customerId}")
    Customer getCustomerDetails(@PathVariable int customerId, @RequestHeader("Authorization") UUID uuid) throws CouponSystemException {
        return customerService.getCustomerDetails(customerId, uuid);
    }

    @GetMapping("available/{customerId}")
    List<Coupon> getAllAvailableCoupons(@PathVariable int customerId) {
        return customerService.getAllAvailableCoupons(customerId);
    }

    @GetMapping("coupons")
    List<CouponDto> getAllCoupons() {
        return couponMapper.toDtoList(customerService.getAllCoupons());
    }

    @GetMapping("oneCoupon/{id}")
    Coupon getOneCoupon(@PathVariable int id) throws CouponSystemException {
        return customerService.getOneCoupon(id);
    }
}
