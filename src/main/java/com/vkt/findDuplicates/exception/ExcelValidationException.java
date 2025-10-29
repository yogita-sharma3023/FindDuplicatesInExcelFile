package com.vkt.findDuplicates.exception;

public class ExcelValidationException extends RuntimeException{
	private final ErrorDetail errorDetail;
	public ExcelValidationException(String message,ErrorDetail errorDetail) {
        super(message);
        this.errorDetail=errorDetail;
    }
	public ExcelValidationException(String message) {
        super(message);
        this.errorDetail = null;
    }
	public ErrorDetail getErrorDetail() {
		return errorDetail;
	}
}
