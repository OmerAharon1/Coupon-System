package com.jb.CouponSystem.services;

import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.exceptions.CouponSystemException;

import java.util.UUID;

public interface UserService {

    UUID login(String email, String password, ClientType clientType) throws CouponSystemException;

    void logOut(UUID uuid);

}
