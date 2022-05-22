package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.dto.RegisterReqDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import com.jb.CouponSystem.mapper.CustomerMapper;
import com.jb.CouponSystem.security.Information;
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

    @Override
    public UUID login(String email, String password) throws CouponSystemException {

        Customer customer = customerRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD));
        int id = customer.getId();

        Information information = new Information(customer.getId(), email, ClientType.CUSTOMER);
        return tokenManager.addToken(information);

    }

    @Override
    public void register(RegisterReqDto registerReqDto) throws CouponSystemException {
        if (customerRepository.existsByEmailAndPassword(registerReqDto.getEmail(), registerReqDto.getPassword())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        customerRepository.save(customerMapper.toCustomer(registerReqDto));
    }

    @Override
    //else is not required
    public void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException {
        Optional<Coupon> optionalCoupon = Optional.ofNullable((couponRepository.findById(coupon.getId()).orElseThrow(ExceptionUtil::IdNotFound)));
        coupon = optionalCoupon.get();

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
        couponRepository.save(coupon);
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
    public Customer getCustomerDetails(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow(ExceptionUtil::IdNotFound);

    }

    public List<Coupon> getAllAvailableCoupons(int customerID) {
        return couponRepository.getAllAvailableCoupons(customerID);

    }


}