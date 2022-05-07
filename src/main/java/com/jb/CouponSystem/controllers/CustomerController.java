package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TokenManager tokenManager;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        UUID uuid = customerService.login(loginReqDto.getEmail(), loginReqDto.getPassword());
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(loginReqDto.getEmail(), uuid, clientType);

    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody Customer customer) throws CouponSystemException {
        customerService.register(customer);
    }

    @PutMapping("/{customerId}")
    void purchaseCoupon(@PathVariable int customerId, @RequestBody Coupon coupon) throws CouponSystemException {
        customerService.purchaseCoupon(customerId, coupon);
    }

    @GetMapping("/{customerId}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId) {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("/{customerId}/{Category}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId, @PathVariable Category category) {
        return customerService.getCustomerCoupons(customerId, category);
    }

    @GetMapping("/{customerId}/{maxPrice}")
    List<Coupon> getCustomerCoupons(@PathVariable int customerId, @RequestParam double maxPrice) {
        return customerService.getCustomerCoupons(customerId, maxPrice);
    }

    @GetMapping("customer/{customerId}")
    Customer getCustomerDetails(@PathVariable int customerId) throws CouponSystemException {
        return customerService.getCustomerDetails(customerId);
    }

    @GetMapping("available/{customerId}")
    List<Coupon> getAllAvailableCoupons(@PathVariable int customerId) {
        return customerService.getAllAvailableCoupons(customerId);
    }

}
