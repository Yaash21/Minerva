package com.stackroute.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Getter;
import lombok.Setter;

@NodeEntity
@Getter @Setter
public class Word {
	@GraphId
	private int id;
	private String name;
	private int weight;
	
	@Relationship(type="Is_A")
	private Intent intent;
	
}