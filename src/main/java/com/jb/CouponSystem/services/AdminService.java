package com.jb.CouponSystem.services;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.exceptions.CouponSystemException;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    void addCompany(Company company) throws CouponSystemException;

    void updateCompany(int companyId, Company company) throws CouponSystemException;

    void deleteCompany(int companyID) throws CouponSystemException;

    List<Company> getAllCompanies();

    Company getOneCompany(int companyID) throws CouponSystemException;

    void addCustomer(Customer customer) throws CouponSystemException;

    void updateCustomer(int customerId, Customer customer, UUID token) throws CouponSystemException;

    void deleteCustomer(int customerId) throws CouponSystemException;

    List<Customer> getAllCustomers();

    Customer getOneCustomer(int customerID, UUID uuid) throws CouponSystemException;

}
