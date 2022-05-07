package com.jb.CouponSystem.clr;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Company;
import com.jb.CouponSystem.Beans.Coupon;
import com.jb.CouponSystem.Beans.Customer;
import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.LoginManager.LoginManager;
import com.jb.CouponSystem.art.Asci;
import com.jb.CouponSystem.repos.CompanyRepository;
import com.jb.CouponSystem.repos.CouponRepository;
import com.jb.CouponSystem.repos.CustomerRepository;
import com.jb.CouponSystem.services.AdminService;
import com.jb.CouponSystem.services.CompanyService;
import com.jb.CouponSystem.services.CustomerService;
import com.jb.CouponSystem.services.CustomerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static com.jb.CouponSystem.Beans.Category.FOOD;

@Component
public class Tests implements CommandLineRunner {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CustomerServiceImp customerServiceImp;
    @Autowired
    private LoginManager loginManager;


    @Override
    public void run(String... args) throws Exception {
        Customer customer = Customer.builder()
                .firstName("moti")
                .lastName("avramov")
                .email("moti@avramov.com")
                .password("123456")
                .build();


        Customer customer2 = Customer.builder()
                .firstName("ela")
                .lastName("shenkin")
                .email("ela@shenkin.com")
                .password("123456")
                .build();

        Customer customer3 = Customer.builder()
                .firstName("avram")
                .lastName("nagasa")
                .email("avram@nagasa.com")
                .password("123456")
                .build();

        Customer customer4 = Customer.builder()
                .firstName("omer")
                .lastName("aharon")
                .email("omer@aharon.com")
                .password("123456")
                .build();


        Company company = Company.builder()
                .name("pepsi")
                .email("pepsi@gmail.com")
                .password("123456")
                .build();

        Company company2 = Company.builder()
                .name("CocaCola")
                .email("CocaCola@gmail.com")
                .password("123456")
                .build();

        Company company3 = Company.builder()
                .name("adminTest")
                .email("test@test.com")
                .password("123456")
                .build();

        Coupon coupon = Coupon.builder()
                .amount(100)
                .price(20)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .title("free coke")
                .description("just get a free coke")
                .category(FOOD)
                .image("nothing")
                .company(companyRepository.getById(1))
                .build();

        Coupon coupon2 = Coupon.builder()
                .amount(100)
                .price(25)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(7)))
                .title("discount coke")
                .description("discount coke")
                .category(Category.GAMING)
                .image("nothing")
                .company(companyRepository.getById(2))
                .build();

        Coupon coupon3 = Coupon.builder()
                .amount(100)
                .price(20)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().minusDays(2)))
                .title("im expired")
                .description("just delete me already")
                .category(FOOD)
                .image("nothing")
                .company(companyRepository.getById(1))
                .build();

        Coupon coupon4 = Coupon.builder()
                .amount(95)
                .price(31)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .title("comp Test")
                .description("let coca cola own me")
                .category(FOOD)
                .image("nothing")
                .company(companyRepository.getById(2))
                .build();

        customerRepository.saveAll(Arrays.asList(customer, customer2));
        companyRepository.saveAll(Arrays.asList(company, company2));
        couponRepository.saveAll(Arrays.asList(coupon, coupon2, coupon3));
        customerRepository.save(customer4);

        //Company Test
        System.out.println(Asci.companyTest);
        CompanyService companyService = (CompanyService) loginManager.login("CocaCola@gmail.com", "123456", ClientType.COMPANY);
        System.out.println("Get all company coupons::\n" + companyService.getCompanyCoupons(2));
        companyService.addCoupon(2, coupon4);
        System.out.println("Add Coupon::\n" + companyService.getCompanyCoupons(2));
        coupon4.setTitle("im a bit diffrent now");
        companyService.updateCoupon(2, coupon4);
        System.out.println("Update Coupon::\n" + companyService.getCompanyCoupons(2));
        companyService.deleteCoupon(4);
        System.out.println("Delete Coupon::\n" + companyService.getCompanyCoupons(2));
        companyService.addCoupon(2, coupon4);
        System.out.println("Get all coupons by category::\n" + companyService.getCompanyCoupons(1, FOOD));
        System.out.println("Get all coupons by max price::\n" + companyService.getCompanyCoupons(2, 25));
        System.out.println("Get one company::\n" + companyService.getCompanyDetails(2));


        //Customer Test
        System.out.println(customerRepository.findByEmailAndPassword("ela@shenkin.com", "123456").get());
//        System.out.println(customerServiceImp.login("ela@shenkin.com", "123456"));
        System.out.println(Asci.customerTest);
        CustomerService customerService = (CustomerService) loginManager.login("ela@shenkin.com", "123456", ClientType.CUSTOMER);
        customerService.purchaseCoupon(2, coupon);
        System.out.println("Get all coupons::\n" + customerService.getCustomerCoupons(2));
        customerService.purchaseCoupon(2, coupon2);
        System.out.println("Purchase coupon::\n" + customerService.getCustomerCoupons(2));
        customerService.purchaseCoupon(1, coupon);
        System.out.println("Get all coupons by max price::\n" + customerService.getCustomerCoupons(2, 20));
        System.out.println("Get all coupons by category::\n" + customerService.getCustomerCoupons(2, FOOD));
        System.out.println("Get one customer::\n" + customerService.getCustomerDetails(2));
        System.out.println("Get all available coupons::\n" + customerService.getAllAvailableCoupons(2));
//        customerService.register(customer4);

        //Admin Test
        System.out.println(Asci.adminTest);
        AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        System.out.println("Get all companies::\n" + adminService.getAllCompanies());
        adminService.addCompany(company3);
        System.out.println("New company::\n" + adminService.getAllCompanies());
        company3.setPassword("123123");
        adminService.updateCompany(company3);
        System.out.println("Update company::\n" + adminService.getAllCompanies());
        adminService.deleteCompany(3);
        System.out.println("Delete company::\n" + adminService.getAllCompanies());
        System.out.println("Get one company::\n" + adminService.getOneCompany(1));
        System.out.println("Get all customers::\n" + adminService.getAllCustomers());
        adminService.addCustomer(customer3);
        System.out.println("Add Customer::\n" + adminService.getAllCustomers());
        customer3.setLastName("mangistu");
        adminService.updateCustomer(customer3);
        System.out.println("Update Customer::\n" + adminService.getAllCustomers());
        adminService.deleteCustomer(3);
        System.out.println("Delete Customer::\n" + adminService.getAllCustomers());
        System.out.println("Get one customer(2)::\n" + adminService.getOneCustomer(2));


    }
}
