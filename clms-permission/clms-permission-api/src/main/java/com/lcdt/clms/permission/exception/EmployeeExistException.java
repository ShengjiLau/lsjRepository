package com.lcdt.clms.permission.exception;

/**
 * Created by lyqishan on 2018/7/26
 */

public class EmployeeExistException extends RuntimeException {

    public EmployeeExistException() {
    }

    public EmployeeExistException(String message) {
        super(message);
    }
}
