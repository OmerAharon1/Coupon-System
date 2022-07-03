package com.jb.CouponSystem.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Coupons")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(unique = true, length = 30)
    private String title;
    private String description;
    private Timestamp startDate;
    private Timestamp endDate;
    private int amount;
    private double price;
    private String image;
}
