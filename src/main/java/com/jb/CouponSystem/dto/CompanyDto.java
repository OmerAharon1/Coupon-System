package com.jb.CouponSystem.dto;

import com.jb.CouponSystem.Beans.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int companyId;
    private String name;
    private String password;
    private String email;


    private List<Coupon> coupons = new ArrayList<>();
}
