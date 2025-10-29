package com.vkt.findDuplicates.dto;

import org.apache.poi.ss.formula.functions.T;

public class APIResponse<T> {
   
	private String status;
    private int code;
    private String message;
    private T content;
    
    public APIResponse(String status, int code, String message, T content) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.content = content;
    }
    public APIResponse() {
    }
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	
}
