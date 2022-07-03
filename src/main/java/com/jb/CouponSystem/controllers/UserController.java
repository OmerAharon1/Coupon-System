package com.jb.CouponSystem.controllers;

import com.jb.CouponSystem.LoginManager.ClientType;
import com.jb.CouponSystem.LoginManager.LoginManager;
import com.jb.CouponSystem.dto.LoginReqDto;
import com.jb.CouponSystem.dto.LoginResDto;
import com.jb.CouponSystem.dto.RegisterReqDto;
import com.jb.CouponSystem.exceptions.CouponSystemException;
import com.jb.CouponSystem.security.TokenManager;
import com.jb.CouponSystem.services.CustomerService;
import com.jb.CouponSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")

public class UserController {
    @Autowired
    LoginManager loginManager;
    @Autowired
    TokenManager tokenManager;
    @Autowired
    UserService userService;
    @Autowired
    CustomerService customerService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterReqDto registerReqDto) throws CouponSystemException {
        customerService.register(registerReqDto);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@RequestBody LoginReqDto loginReqDto) throws CouponSystemException {
        UUID uuid = userService.login(loginReqDto.getEmail(), loginReqDto.getPassword(), loginReqDto.getClientType());
        ClientType clientType = tokenManager.getClientType(uuid);
        return new LoginResDto(tokenManager.getIdByToken(uuid), loginReqDto.getEmail(), uuid, clientType);

    }

    @PutMapping("logout/{uuid}")
    public void logout(@RequestParam UUID uuid) {
        userService.logOut(uuid);
    }

}
