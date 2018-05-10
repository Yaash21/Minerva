package com.stackroute.redisson;

import org.springframework.stereotype.Component;

@Component
public class IntentModel {
	private String term;
	private String intent;
	private String weight;
	
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}

	

}
