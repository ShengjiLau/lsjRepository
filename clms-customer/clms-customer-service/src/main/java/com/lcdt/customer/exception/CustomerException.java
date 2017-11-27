package com.lcdt.customer.exception;

/**
 * Created by yangbinq on 2017/11/23.
 */
public class CustomerException extends RuntimeException {

    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
