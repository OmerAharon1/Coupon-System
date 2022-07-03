package com.jb.CouponSystem.Beans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ToString.Exclude
    @Singular
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Coupon> coupons = new ArrayList<>();


}
