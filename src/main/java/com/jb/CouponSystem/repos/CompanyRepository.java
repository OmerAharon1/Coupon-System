package com.jb.CouponSystem.repos;

import com.jb.CouponSystem.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByEmailAndPassword(String email, String password);

    Optional<Company> findByIdAndName(int companyId, String name);

    Optional<Company> findByEmailAndPassword(String email, String password);

}
