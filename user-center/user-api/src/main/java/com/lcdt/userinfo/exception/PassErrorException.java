package com.lcdt.userinfo.exception;

/**
 * Created by ss on 2017/8/18.
 */
public class PassErrorException extends RuntimeException {

    public PassErrorException() {
    }

    public PassErrorException(String message) {
        super(message);
    }

    public PassErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PassErrorException(Throwable cause) {
        super(cause);
    }

    public PassErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
