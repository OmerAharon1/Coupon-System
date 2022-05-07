package com.jb.CouponSystem.Beans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Companies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, length = 40, updatable = false)
    private String name;
    private String password;
    @Column(unique = true, length = 40)
    private String email;

    @OneToMany
    @ToString.Exclude
    @Singular
    private List<Coupon> coupons;


}
