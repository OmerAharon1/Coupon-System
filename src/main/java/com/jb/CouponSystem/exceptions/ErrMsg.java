package com.jb.CouponSystem.exceptions;

public enum ErrMsg {

    ID_NOT_EXISTS("Id not found"),
    NOT_FOUND("entity not found"),
    ALREADY_EXISTS("already exists"),
    FALSE_EMAIL_OR_PASSWORD("false email or password"),
    COUPON_AMOUNT_NOT_VALID("coupon amount lower then 1"),
    ALREADY_OWN_COUPON("user already own coupon"),
    EXPIRED_COUPON("coupon is expired");


    private String description;

    ErrMsg(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
