package com.stackroute.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.stackroute.redisson.FetchUrl;

@Component
public class UserOutput {
	
	private String sessionId;
	private String type;
	private ArrayList<FetchUrl> response;
	private String message;
	private boolean illustration;
	public boolean isIllustration() {
		return illustration;
	}
	public void setIllustration(boolean illustration) {
		this.illustration = illustration;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<FetchUrl> getResponse() {
		return response;
	}
	public void setResponse(ArrayList<FetchUrl> response) {
		this.response = response;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
