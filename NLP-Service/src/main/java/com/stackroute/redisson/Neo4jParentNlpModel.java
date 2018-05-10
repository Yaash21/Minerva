package com.stackroute.redisson;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Neo4jParentNlpModel {

	private Map<String, List<String>> parentNodes;

	public Map<String, List<String>> getParentNodes() {
		return parentNodes;
	}

	public void setParentNodes(Map<String, List<String>> parentNodes) {
		this.parentNodes = parentNodes;
	}

}
