package com.jb.CouponSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterReqDto {

    private String email;
    private String password;
}
