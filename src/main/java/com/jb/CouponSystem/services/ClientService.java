package com.jb.CouponSystem.services;

import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.security.TokenManager;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected TokenManager tokenManager;


}
