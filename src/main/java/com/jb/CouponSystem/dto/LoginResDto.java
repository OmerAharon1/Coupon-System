package com.jb.CouponSystem.dto;

import com.jb.CouponSystem.LoginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LoginResDto {

    private int id;
    private String email;
    private UUID token;
    private ClientType clientType;
}
