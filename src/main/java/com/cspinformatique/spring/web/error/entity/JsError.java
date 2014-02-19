package com.cspinformatique.spring.web.error.entity;

public class JsError {
	private String message;
	private String fileName;
	private int lineNumber;
	private int columnNumber;
	private String userAgent;
	private String userIp;
	private String userOs;
	
	public JsError(){
		
	}

	public JsError(
		String message, 
		String fileName, 
		int lineNumber,
		int columnNumber, 
		String userAgent, 
		String userIp, 
		String userOs
	){
		this.message = message;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.userAgent = userAgent;
		this.userIp = userIp;
		this.userOs = userOs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserOs() {
		return userOs;
	}

	public void setUserOs(String userOs) {
		this.userOs = userOs;
	}
}
