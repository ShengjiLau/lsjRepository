package com.lcdt.userinfo.exception;

/**
 * Created by Administrator on 2018/5/7.
 */
public class TokenFailureException extends RuntimeException {
    public TokenFailureException(String message) {
        super(message);
    }
}
