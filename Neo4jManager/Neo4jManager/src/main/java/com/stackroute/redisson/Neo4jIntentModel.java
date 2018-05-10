package com.stackroute.redisson;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Neo4jIntentModel {

	private ArrayList<IntentModel> intentList;

	public ArrayList<IntentModel> getIntentList() {
		return intentList;
	}

	public void setIntentList(ArrayList<IntentModel> intentList) {
		this.intentList = intentList;
	}
	
}
