package com.stackroute.redisson;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class TermAngularModel {
	private ArrayList<IntentModel> neo4jList;
	
	public ArrayList<IntentModel> getNeo4jList() {
		return neo4jList;
	}

	public void setNeo4jList(ArrayList<IntentModel> neo4jList) {
		this.neo4jList = neo4jList;
	}



}
