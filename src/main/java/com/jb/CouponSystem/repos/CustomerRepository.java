package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmailAndPassword(String email, String password);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) from customers_coupons where coupons_id =:coupons_id and customer_id =:customer_id ;")
    int findCustomerOwnCouponById(@Param("customer_id") int customer_id, @Param("coupons_id") int coupons_id);

    Optional<Customer> findByEmailAndPassword(String email, String password);


}
