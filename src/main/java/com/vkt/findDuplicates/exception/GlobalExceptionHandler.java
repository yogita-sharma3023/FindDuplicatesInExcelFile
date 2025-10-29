package com.vkt.findDuplicates.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vkt.findDuplicates.dto.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ExcelValidationException.class)
    public ResponseEntity<APIResponse<Object>> handleExcelValidationException(ExcelValidationException ex) {
		System.err.println("ExcelValidationException: " + ex.getMessage());
		APIResponse<Object> response = new APIResponse<>(
		    "FAILED",
		    HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),  // message from thrown exception
            ex.getErrorDetail()
	    );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(ServiceException.class)
    public ResponseEntity<APIResponse<Object>> handleServiceException(ServiceException ex) {
        APIResponse<Object> response = new APIResponse<>(
                "FAILED",
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrorMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<Object>> handleRuntimeException(RuntimeException ex) {
		System.err.println("RunTimeException: " + ex.getMessage());
        APIResponse<Object> response = new APIResponse<>(
                "FAILED",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), // use your constants
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
