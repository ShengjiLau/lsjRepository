package com.lcdt.util.validate;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Created by ss on 2017/11/3.
 */
public class ValidateException extends RuntimeException {

	public ValidateException() {
	}

	public ValidateException(List<ObjectError> errorList) {
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public ValidateException(String message) {
		super(message);
	}
}
