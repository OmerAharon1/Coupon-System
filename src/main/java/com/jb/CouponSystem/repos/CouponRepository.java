package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.Beans.Category;
import com.jb.CouponSystem.Beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `couponsystem`.`customers_coupons` (`customer_id`, `coupons_id`) VALUES (:customer_id , :coupons_id)", nativeQuery = true)
    void buyCoupon(@Param("customer_id") int customerId, @Param("coupons_id") int couponId);

    @Query(value = "SELECT * from coupons where id in (select coupons_id from customers_coupons where customer_id = :customer_id)", nativeQuery = true)
    List<Coupon> getAllCustomerCoupons(@Param("customer_id") int customerID);

    @Query(value = "SELECT * from coupons where price <= :price and id in (select coupons_id  from customers_coupons where customer_id = :customer_id)", nativeQuery = true)
    List<Coupon> getAllCustomerCoupons(@Param("customer_id") int customerID, @Param("price") double maxPrice);

    @Query(value = "SELECT * from coupons where category = :#{#category?.name()} and id in (select coupons_id  from customers_coupons where customer_id = :customer_id) ", nativeQuery = true)
    List<Coupon> getAllCustomerCoupons(@Param("customer_id") int customerID, @Param("category") Category category);

    @Query(value = "select * from coupons where company_id = :company_id", nativeQuery = true)
    List<Coupon> getAllCompanyCoupons(@Param("company_id") int companyID);

    List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price);

    @Query(value = "SELECT * from coupons where category = :#{#category?.name()} and company_id = :company_id ", nativeQuery = true)
    List<Coupon> getAllCompanyCoupons(@Param("company_id") int companyID, @Param("category") Category category);

    @Query(value = "select * from coupons where :company_id = company_id and title = :title ", nativeQuery = true)
    Coupon findCouponByTitle(@Param("company_id") int companyId, @Param("title") String title);

    @Modifying
    @Transactional
    @Query(value = "DELETE from customers_coupons where coupons_id = :couponID", nativeQuery = true)
    void deleteCustomerCouponPurchases(int couponID);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from coupons where end_date < curdate()")
    void deleteExpiredCoupons();

    List<Coupon> findByIdAndEndDateBefore(int couponId, Date date);

    @Modifying
    @Transactional
    @Query(value = "delete from customers_coupons where coupons_id in (select id from coupons where company_id = :compId);", nativeQuery = true)
    void deletePurchasesByCompId(int compId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from coupons where company_id = :compId")
    void deleteByCompanyId(int compId);

    @Query(nativeQuery = true, value = "SELECT * FROM couponsystem.coupons where id not in(select coupons_id from customers_coupons where customer_id = :customerID);")
    List<Coupon> getAllAvailableCoupons(int customerID);

}
