package com.jb.CouponSystem.LoginManager;

import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.services.AdminServiceImp;
import com.jb.CouponSystem.services.ClientService;
import com.jb.CouponSystem.services.CompanyServiceImp;
import com.jb.CouponSystem.services.CustomerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    private CompanyServiceImp companyService;
    @Autowired
    private CustomerServiceImp customerService;
    @Autowired
    private AdminServiceImp adminService;

    public ClientService login(String email, String password, ClientType type) throws CouponSystemException {
        switch (type) {
            case ADMINISTRATOR:
                return adminService;
            case COMPANY:
                return companyService;
            case CUSTOMER:
                return customerService;

        }
        throw new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD);
    }
}
