package com.stackroute.model;

import org.springframework.stereotype.Component;

@Component
public class UserInput {
	
	private String domain;
	private String concept;
	private String intent;
	private String type;
	private String message;
	private String sessionId;
	private boolean illustration;
	private int moreUrl;
	
	public int getMoreUrl() {
		return moreUrl;
	}
	public void setMoreUrl(int moreUrl) {
		this.moreUrl = moreUrl;
	}
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}

	

}
