package com.lcdt.userinfo.exception;

/**
 * Created by ss on 2017/8/1.
 */
public class PhoneHasRegisterException extends Exception {

    public PhoneHasRegisterException() {
    }

    public PhoneHasRegisterException(String message) {
        super(message);
    }

    public PhoneHasRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneHasRegisterException(Throwable cause) {
        super(cause);
    }

    public PhoneHasRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
