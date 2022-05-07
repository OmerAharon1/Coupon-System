package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        UUID uuid = companyService.login(loginReqDto.getEmail(), loginReqDto.getPassword());
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(loginReqDto.getEmail(), uuid, clientType);

    }

    @PostMapping("/{Id}")
    void addCoupon(@PathVariable int companyID, @RequestBody Coupon coupon) throws CouponSystemException {
        companyService.addCoupon(companyID, coupon);
    }

    @PutMapping("/{Id}")
    void updateCoupon(@PathVariable int companyID, @RequestBody Coupon coupon) {
        companyService.updateCoupon(companyID, coupon);
    }

    @DeleteMapping("/{Id}")
    void deleteCoupon(int CouponID) throws CouponSystemException {
        companyService.deleteCoupon(CouponID);
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
