package com.stackroute.redisson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Neo4jParentNlpModel {
	
	private Map<String,ArrayList<String>> parentNodes;

	public Map<String,ArrayList<String>> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(Map<String,ArrayList<String>> parentNodes) {
		this.parentNodes = parentNodes;
	}

}
