package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.mapper.CompanyMapper;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private CompanyMapper companyMapper;


    @PostMapping("addCoupon/{companyID}")
    CouponDto addCoupon(@RequestHeader("Authorization") UUID uuid, @PathVariable int companyID, @RequestBody CouponDto couponDto) throws CouponSystemException {
        return companyService.addCoupon(companyID, couponDto, uuid);
    }

    @PutMapping("updateCoupon/{couponId}")
    CouponDto updateCoupon(@RequestHeader("Authorization") UUID uuid, @PathVariable int couponId, @RequestBody CouponDto couponDto) throws CouponSystemException {
        return companyService.updateCoupon(couponId, couponDto, uuid);
    }

    @DeleteMapping("delete/{id}")
    void deleteCoupon(@PathVariable int id) throws CouponSystemException {
        companyService.deleteCoupon(id);
    }

    @GetMapping("coupons/{Id}")
    List<Coupon> getCompanyCoupons(@PathVariable int companyID) {
        return companyService.getCompanyCoupons(companyID);
    }

    @GetMapping("/{companyID}/{Category}")
    List<Coupon> getCompanyCoupons(@PathVariable int companyID, @PathVariable Category category) {
        return companyService.getCompanyCoupons(companyID, category);
    }

    @GetMapping("/{companyID}/{maxPrice}")
    List<Coupon> getCompanyCoupons(@PathVariable int companyID, @RequestParam int maxPrice) {
        return companyService.getCompanyCoupons(companyID, maxPrice);
    }

    @GetMapping("/{companyID}")
    Company getCompanyDetails(@PathVariable int companyID) throws CouponSystemException {
        return companyService.getCompanyDetails(companyID);
    }

}
