package com.lcdt.warehouse.controller.exception;

/**
 * Created by xrr on 2018/8/14.
 * 库存处理异常捕获
 */
public class InventoryException extends RuntimeException{
    private String message;

    public InventoryException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
