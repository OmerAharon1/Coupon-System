package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CouponDto addCoupon(int companyID, CouponDto couponDto, UUID uuid) throws CouponSystemException;

    CouponDto updateCoupon(int companyID, CouponDto couponDto, UUID uuid) throws CouponSystemException;

    void deleteCoupon(int CouponID) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyID);

    List<Coupon> getCompanyCoupons(int companyID, Category category);

    List<Coupon> getCompanyCoupons(int companyID, int maxPrice);

    Company getCompanyDetails(int companyID) throws CouponSystemException;

}
