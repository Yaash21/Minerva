package com.stackroute.redisson;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Neo4jIntentModel {

	private List<IntentModel> intentList;

	public List<IntentModel> getIntentList() {
		return intentList;
	}

	public void setIntentList(List<IntentModel> intentList) {
		this.intentList = intentList;
	}
	
}
