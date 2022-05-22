package com.jb.CouponSystem.services;


import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.dto.RegisterReqDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    UUID login(String email, String password) throws CouponSystemException;

    void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException;

    List<Coupon> getCustomerCoupons(int customerId);

    List<Coupon> getCustomerCoupons(int customerId, Category category);

    List<Coupon> getCustomerCoupons(int customerId, double maxPrice);

    Customer getCustomerDetails(int customerId) throws CouponSystemException;

    List<Coupon> getAllAvailableCoupons(int customerID);

    void register(RegisterReqDto registerReqDto) throws CouponSystemException;


}
