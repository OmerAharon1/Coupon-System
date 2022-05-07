package com.jb.CouponSystem;

import com.jb.CouponSystem.exceptions.CouponSystemException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class CouponSystemApplication {


    public static void main(String[] args) throws CouponSystemException {
        SpringApplication.run(CouponSystemApplication.class, args);
        System.out.println("\n All Tests Complete - Program is running");


    }
}