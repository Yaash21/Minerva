package com.stackroute.redisson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Neo4jConceptModel {

	
	private Map<String,ArrayList<String>> conceptList;

	public Map<String, ArrayList<String>> getConceptList() {
		return conceptList;
	}

	public void setConceptList(Map<String, ArrayList<String>> conceptList) {
		this.conceptList = conceptList;
	}
	

}
