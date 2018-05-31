package com.lcdt.warehouse.controller.exception;

/**
 * Created by ss on 2017/11/9.
 */
public class WareExistException extends RuntimeException {
	public WareExistException() {
	}

	public WareExistException(String message) {
		super(message);
	}
}
