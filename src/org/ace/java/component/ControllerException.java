package org.ace.java.component;

import org.springframework.transaction.TransactionSystemException;

public class ControllerException extends TransactionSystemException {
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private Object response;

	public ControllerException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ControllerException(String errorCode, Throwable throwable) {
		super(errorCode, throwable);
		this.errorCode = errorCode;
	}

	public ControllerException(String errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public ControllerException(String errorCode, Object response, String message) {
		super(message);
		this.errorCode = errorCode;
		this.response = response;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object getResponse() {
		return response;
	}
}
