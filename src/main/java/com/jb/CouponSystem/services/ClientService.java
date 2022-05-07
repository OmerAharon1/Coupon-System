package com.jb.CouponSystem.services;

import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@NoArgsConstructor
@Service
public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;

    public abstract UUID login(String email, String password) throws CouponSystemException;
}
