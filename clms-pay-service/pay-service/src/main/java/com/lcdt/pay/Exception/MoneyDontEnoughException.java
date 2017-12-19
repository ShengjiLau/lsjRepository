package com.lcdt.pay.Exception;

public class MoneyDontEnoughException extends RuntimeException {
    public MoneyDontEnoughException(String message) {
        super(message);
    }
}
