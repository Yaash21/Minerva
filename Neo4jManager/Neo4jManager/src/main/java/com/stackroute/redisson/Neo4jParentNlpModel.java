package com.stackroute.redisson;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Neo4jParentNlpModel {
	
	private ArrayList<String> parentNodes;

	public ArrayList<String> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(ArrayList<String> parentNodes) {
		this.parentNodes = parentNodes;
	}

}
