package com.stackroute.redisson;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Neo4jConceptNlpModel {
	
	private List<ConceptNlpModel> conceptList;

	public List<ConceptNlpModel> getConceptList() {
		return conceptList;
	}

	public void setConceptList(List<ConceptNlpModel> conceptList) {
		this.conceptList = conceptList;
	}

}
