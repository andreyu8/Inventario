package com.seidor.inventario.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String msg) {
		super(msg);
		this.message = msg;
	}

	public BusinessException(RuntimeException ex) {
		super(ex);
	}
	
	public BusinessException(String msg, RuntimeException rtEx) {
		super(msg, rtEx);
		rtEx.printStackTrace();
		this.message = msg;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

