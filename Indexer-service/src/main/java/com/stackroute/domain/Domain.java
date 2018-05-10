package com.stackroute.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Domain {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	@Relationship(type = "ConceptOf", direction = Relationship.INCOMING)
	List<Concept> concepts = new ArrayList<>();

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<Concept> getConcepts() {
		return this.concepts;
	}

}
