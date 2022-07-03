package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.mapper.CustomerMapper;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.security.Information;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
    private final String email = "admin@admin.com";
    private final String password = "admin";
    @Autowired
    TokenManager tokenManager;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Override
    public UUID login(String email, String password, ClientType clientType) throws CouponSystemException {
        Information information;
        switch (clientType) {
            case CUSTOMER:
                Customer customer = customerRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD));
                int id = customer.getId();
                information = new Information(customer.getId(), email, ClientType.CUSTOMER);
                return tokenManager.addToken(information);
            case COMPANY:

                Company company = companyRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD));

                int companyID = company.getId();

                information = new Information(companyID, email, ClientType.COMPANY);
                return tokenManager.addToken(information);
            case ADMINISTRATOR:
                System.out.println(email);
                if (!Objects.equals(email, this.email) || !Objects.equals(password, this.password)) {
                    throw new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD);
                }

                information = new Information(0, email, ClientType.ADMINISTRATOR);
                return tokenManager.addToken(information);


        }
        throw new CouponSystemException(ErrMsg.NOT_FOUND);
    }

    @Override
    public void logOut(UUID uuid) {
        tokenManager.deleteByToken(uuid);

    }


}
