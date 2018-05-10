package com.stackroute.redisson;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Neo4jConceptNlpModel {

	private ArrayList<ConceptNlpModel> conceptList;

	public ArrayList<ConceptNlpModel> getConceptList() {
		return conceptList;
	}

	public void setConceptList(ArrayList<ConceptNlpModel> conceptList) {
		this.conceptList = conceptList;
	}
}
