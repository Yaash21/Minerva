package com.stackroute.redisson;

import org.springframework.stereotype.Component;

@Component
public class ConceptNlpModel {
	
	private String concept;
	private String domain;
	
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	

}
