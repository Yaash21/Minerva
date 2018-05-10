package com.stackroute.domain;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Root {
	
	@GraphId
	private int id;
	private String name;
	
	@Relationship(type="Is_An", direction = Relationship.INCOMING)	 
    private List<Is_An> intents;
	
	
	public List<Is_An> getIntents() {
		return intents;
	}
	public void setIntents(List<Is_An> intents) {
		this.intents = intents;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	

}

