package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import com.jb.CouponSystem.security.Information;
import com.jb.CouponSystem.security.TokenManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImp extends ClientService implements AdminService {

    private final String email = "admin@admin.com";
    private final String password = "admin";
    private TokenManager tokenManager;

    @Override
    public UUID login(String email, String password) throws CouponSystemException {
        if (email != this.email || password != this.password) {
            throw new CouponSystemException(ErrMsg.FALSE_EMAIL_OR_PASSWORD);
        }
        Information information = new Information(0, "admin@admin.com", ClientType.ADMINISTRATOR);
        return tokenManager.addToken(information);


    }

    @Override
    public void addCompany(Company company) throws CouponSystemException {
        if (companyRepository.existsByEmailAndPassword(company.getEmail(), company.getPassword())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(Company company) throws CouponSystemException {
        companyRepository.findByIdAndName(company.getId(), company.getName()).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_NOT_EXISTS));
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(int companyID) throws CouponSystemException {
        companyRepository.findById(companyID).orElseThrow(ExceptionUtil::IdNotFound);
        //todo cascade
        couponRepository.deletePurchasesByCompId(companyID);
        couponRepository.deleteByCompanyId(companyID);
        companyRepository.deleteById(companyID);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getOneCompany(int companyID) throws CouponSystemException {
        return companyRepository.findById(companyID).orElseThrow(ExceptionUtil::IdNotFound);
    }

    @Override
    public void addCustomer(Customer customer) throws CouponSystemException {
        if (customerRepository.existsByEmailAndPassword(customer.getEmail(), customer.getPassword())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws CouponSystemException {
        this.customerRepository.findById(customer.getId()).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_NOT_EXISTS));
        this.customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        this.customerRepository.findById(customerId).orElseThrow(ExceptionUtil::IdNotFound);
        this.customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getOneCustomer(int customerID) throws CouponSystemException {
        return this.customerRepository.findById(customerID).orElseThrow(ExceptionUtil::IdNotFound);
    }


}
