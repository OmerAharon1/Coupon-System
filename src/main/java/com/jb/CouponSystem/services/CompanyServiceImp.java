package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import com.jb.CouponSystem.mapper.CouponMapper;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImp extends ClientService implements CompanyService {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private CouponMapper couponMapper;


    @Override
    public CouponDto addCoupon(int companyID, CouponDto couponDto, UUID uuid) throws CouponSystemException {
        if (tokenManager.getIdByToken(uuid) != companyID) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);

        }
        if (null != couponRepository.findCouponByTitle(companyID, couponDto.getTitle())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }

        Coupon coupon = couponMapper.toBean(couponDto);

        Company company = companyRepository.getById(companyID);

        coupon.setCompany(company);

        return couponMapper.toDto(couponRepository.saveAndFlush(coupon));

    }

    @Override
    public CouponDto updateCoupon(int couponId, CouponDto couponDto, UUID uuid) throws CouponSystemException {
        if (tokenManager.getUserID(uuid) != couponDto.getCompanyId()) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);
        }
        Coupon originalCoupons = couponRepository.findById(couponId).orElseThrow(() ->
                new CouponSystemException(ErrMsg.NOT_FOUND));

        if (originalCoupons.getId() != couponDto.getId() && originalCoupons.getCompany().getId() != couponDto.getCompanyId()) {
            throw new CouponSystemException(ErrMsg.ID_NOT_EXISTS);
        }
        Coupon coupon = couponMapper.toBean(couponDto);
        return couponMapper.toDto(couponRepository.saveAndFlush(coupon));
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
