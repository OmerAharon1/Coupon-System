package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.dto.CompanyDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.mapper.CompanyMapper;
import com.jb.CouponSystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyMapper companyMapper;

    @PostMapping("addCompany")
    void addCompany(@RequestBody CompanyDto companyDto) throws CouponSystemException {
        adminService.addCompany(companyMapper.toBean(companyDto));
    }

    @PutMapping("updateCompany/{companyId}")
//    void updateCompany(@PathVariable int companyId,@RequestBody Company company, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
    void updateCompany(@PathVariable int companyId, @RequestBody CompanyDto companyDto) throws CouponSystemException {

//            adminService.updateCompany(companyId,company,token);
        adminService.updateCompany(companyId, companyMapper.toBean(companyDto));

    }

    @DeleteMapping("deleteCompany/{companyId}")
    void deleteCompany(@PathVariable int companyId) throws CouponSystemException {
        adminService.deleteCompany(companyId);
    }

    @GetMapping("company")
    List<Company> getAllCompanies() {
        return adminService.getAllCompanies();
    }

    @GetMapping("company/{companyId}")
    Company getOneCompany(@PathVariable int companyId) throws CouponSystemException {
        return adminService.getOneCompany(companyId);
    }

    @PostMapping("customer")
    void addCustomer(@RequestBody Customer customer, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.addCustomer(customer);
    }

    @PutMapping("customer/{customerId}")
    void updateCustomer(@PathVariable int customerId, @RequestBody Customer customer, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        adminService.updateCustomer(customerId, customer, token);
    }

    @DeleteMapping("customer/{customerId}")
    void deleteCustomer(@PathVariable int customerId) throws CouponSystemException {
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("customers")
    List<Customer> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    @GetMapping("customer/{customerID}")
    Customer getOneCustomer(@PathVariable int customerID, @RequestHeader("Authorization") UUID token) throws CouponSystemException {
        return adminService.getOneCustomer(customerID, token);
    }

}
