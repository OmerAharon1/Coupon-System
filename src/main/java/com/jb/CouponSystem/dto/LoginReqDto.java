package com.jb.CouponSystem.dto;


import com.jb.CouponSystem.LoginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {

    private String email;
    private String password;
    private ClientType clientType;


}
