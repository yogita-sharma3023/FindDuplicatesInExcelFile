package com.vkt.findDuplicates.exception;

public class ServiceException extends RuntimeException{

	private String errorMessage;

    public ServiceException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
