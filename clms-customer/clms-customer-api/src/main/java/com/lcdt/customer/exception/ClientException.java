package com.lcdt.customer.exception;

/**
 * Created by yangbinq on 2017/11/23.
 */
public class ClientException extends RuntimeException {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }
}
