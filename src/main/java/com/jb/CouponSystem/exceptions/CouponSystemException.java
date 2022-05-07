package com.jb.CouponSystem.exceptions;

public class CouponSystemException extends Exception {

    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getDescription());
    }
}
