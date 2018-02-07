package com.lcdt.userinfo.exception;

/**
 * Created by yangbinq on 2017/10/26.
 */
public class RoleExistException extends RuntimeException {

    public RoleExistException() {
    }

    public RoleExistException(String message) {
        super(message);
    }
}
