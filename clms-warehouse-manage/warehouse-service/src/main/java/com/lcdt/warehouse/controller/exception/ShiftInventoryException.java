package com.lcdt.warehouse.controller.exception;

public class ShiftInventoryException extends RuntimeException{
	
	private String message;
	
	public ShiftInventoryException(String message) {
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
