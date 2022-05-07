package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("company")
    void addCompany(@RequestBody Company company, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.addCompany(company);
    }

    @DeleteMapping("company/{companyID}")
    void deleteCompany(@PathVariable int companyID, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.deleteCompany(companyID);
    }

    @GetMapping("company")
    List<Company> getAllCompanies() {
        return adminService.getAllCompanies();
    }

    @GetMapping("company/{companyID}")
    Company getOneCompany(@PathVariable int companyID, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return adminService.getOneCompany(companyID);
    }

    @PostMapping("customer")
    void addCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.addCustomer(customer);
    }

    @PutMapping("customer")
    void updateCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.updateCustomer(customer);
    }

    @DeleteMapping("customer/{customerId}")
    void deleteCustomer(@PathVariable int customerId, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.deleteCustomer(customerId);
    }

    @GetMapping
    List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) {
        return adminService.getAllCustomers();
    }

    @GetMapping("customer/{customerID}")
    Customer getOneCustomer(@PathVariable int customerID, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return adminService.getOneCustomer(customerID);
    }

}
