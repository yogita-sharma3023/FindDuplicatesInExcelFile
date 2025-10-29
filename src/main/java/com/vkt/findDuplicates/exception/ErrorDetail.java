package com.vkt.findDuplicates.exception;

public class ErrorDetail {
	private int rowNumber;
    private String field;
    private String issue;

    public ErrorDetail(int rowNumber, String field, String issue) {
        this.rowNumber = rowNumber;
        this.field = field;
        this.issue = issue;
    }

   
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }
}
