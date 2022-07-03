package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.dto.CouponDto;
import com.jb.CouponSystem.dto.RegisterReqDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import com.jb.CouponSystem.mapper.CouponMapper;
import com.jb.CouponSystem.mapper.CustomerMapper;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImp extends ClientService implements CustomerService {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CouponMapper couponMapper;

    @Override
    public void register(RegisterReqDto registerReqDto) throws CouponSystemException {
        if (customerRepository.existsByEmailAndPassword(registerReqDto.getEmail(), registerReqDto.getPassword())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        System.out.println(customerMapper.toBean(registerReqDto));
        customerRepository.save(customerMapper.toBean(registerReqDto));
    }

    @Override
    //else is not required
    public CouponDto purchaseCoupon(int customerId, CouponDto couponDto, UUID uuid) throws CouponSystemException {

        Coupon coupon = Optional.ofNullable((couponRepository.findById(couponDto.getId()).orElseThrow(ExceptionUtil::IdNotFound))).get();

        if (tokenManager.getUserID(uuid) != customerId) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);
        }

        if (coupon.getAmount() < 1) {
            throw new CouponSystemException(ErrMsg.COUPON_AMOUNT_NOT_VALID);
        }

        if (customerRepository.findCustomerOwnCouponById(customerId, coupon.getId()) > 0) {
            throw new CouponSystemException(ErrMsg.ALREADY_OWN_COUPON);
        }

        if (!couponRepository.findByIdAndEndDateBefore(coupon.getId(), Date.valueOf(LocalDate.now())).isEmpty()) {
            throw new CouponSystemException(ErrMsg.EXPIRED_COUPON);
        }

        couponRepository.buyCoupon(customerId, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        return couponMapper.toDto(couponRepository.save(coupon));
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId) {
        return couponRepository.getAllCustomerCoupons(customerId);
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, Category category) {
        return couponRepository.getAllCustomerCoupons(customerId, category);
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, double maxPrice) {
        return couponRepository.getAllCustomerCoupons(customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int customerId, UUID uuid) throws CouponSystemException {
        if (tokenManager.getUserID(uuid) != customerId) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);
        }
        ;
        return customerRepository.findById(customerId).orElseThrow(ExceptionUtil::IdNotFound);

    }

    public List<Coupon> getAllAvailableCoupons(int customerID) {
        return couponRepository.getAllAvailableCoupons(customerID);

    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();

    }

    @Override
    public Coupon getOneCoupon(int id) throws CouponSystemException {
        return couponRepository.findById(id).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_NOT_EXISTS));

    }


}