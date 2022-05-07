package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import com.jb.CouponSystem.security.Information;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class CompanyServiceImp extends ClientService implements CompanyService {
    @Autowired
    private TokenManager tokenManager;

    @Override
    public UUID login(String email, String password) throws CouponSystemException {

        Company company = companyRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD));

        int companyID = company.getId();

        Information information = new Information(companyID, email, ClientType.COMPANY);
        return tokenManager.addToken(information);

    }


    @Override
    public void addCoupon(int companyID, Coupon coupon) throws CouponSystemException {
        if (null != couponRepository.findCouponByTitle(companyID, coupon.getTitle())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        couponRepository.save(coupon);

    }

    @Override
    public void updateCoupon(int companyID, Coupon coupon) {
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void deleteCoupon(int couponID) throws CouponSystemException {
        Coupon coupon = couponRepository.findById(couponID).orElseThrow(ExceptionUtil::IdNotFound);
        couponRepository.delete(coupon);
        couponRepository.deleteCustomerCouponPurchases(couponID);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyID) {
        return couponRepository.getAllCompanyCoupons(companyID);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyID, Category category) {
        return couponRepository.getAllCompanyCoupons(companyID, category);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyID, int maxPrice) {
        return couponRepository.findByCompanyIdAndPriceLessThanEqual(companyID, maxPrice);

    }

    @Override
    public Company getCompanyDetails(int companyID) throws CouponSystemException {
        return companyRepository.findById(companyID).orElseThrow(ExceptionUtil::IdNotFound);
    }

}
