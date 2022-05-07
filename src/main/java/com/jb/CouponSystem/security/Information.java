package com.jb.CouponSystem.security;

import com.jb.CouponSystem.LoginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Information {

    private int id;
    private String email;
    private ClientType clientType;
    private LocalDateTime time = LocalDateTime.now();

    public Information(int id, String email, ClientType clientType) {
        this.id = id;
        this.email = email;
        this.clientType = clientType;
    }
}
