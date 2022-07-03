package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.exceptions.ErrMsg;
import com.jb.CouponSystem.exceptions.ExceptionUtil;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Data
public class AdminServiceImp extends ClientService implements AdminService {

    @Override
    public void addCompany(Company company) throws CouponSystemException {
        if (companyRepository.existsByEmailAndPassword(company.getEmail(), company.getPassword())) {
            throw new CouponSystemException(ErrMsg.ALREADY_EXISTS);
        }
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws CouponSystemException {
//        if(!tokenManager.isAdmin(token)){
//            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);}
        companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_NOT_EXISTS));
        company.setId(companyId);
        companyRepository.saveAndFlush(company);
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
    public void updateCustomer(int customerId, Customer customer, UUID token) throws CouponSystemException {
        if (!tokenManager.isAdmin(token)) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);
        }
        customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.ID_NOT_EXISTS));
        customer.setId(customerId);
        customerRepository.save(customer);
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
    public Customer getOneCustomer(int customerID, UUID uuid) throws CouponSystemException {
        if (!tokenManager.isAdmin(uuid)) {
            throw new CouponSystemException(ErrMsg.UNAUTHORIZED);

        }
        return this.customerRepository.findById(customerID).orElseThrow(ExceptionUtil::IdNotFound);
    }


}
