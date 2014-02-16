package com.cspinformatique.wevan.entity;

public class JsError {
	private String message;
	private String source;
	private int line;
	private String userAgent;
	private String userIp;
	private String userOs;
	
	public JsError(){
		
	}
	
	public JsError(String message, String source, int line, String userAgent, String userIp, String userOs){
		this.message = message;
		this.source = source;
		this.line = line;
		this.userAgent = userAgent;
		this.userIp = userIp;
		this.userOs = userOs;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String message){
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
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
