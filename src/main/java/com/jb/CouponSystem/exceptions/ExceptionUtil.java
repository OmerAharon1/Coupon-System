package com.jb.CouponSystem.exceptions;

public class ExceptionUtil {

    public static CouponSystemException IdNotFound() {
        return new CouponSystemException(ErrMsg.ID_NOT_EXISTS);
    }

}
