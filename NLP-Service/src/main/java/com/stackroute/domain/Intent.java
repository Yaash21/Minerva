package com.stackroute.domain;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@NodeEntity
public class Intent {
	
	@GraphId
	private int id;
	private String name;
	
	@Relationship(type="Is_A", direction = Relationship.INCOMING)	 
    private List<Is_A> indicators;
	
	
	@Relationship(type="Is_An")
	private Root root;
	
	public Root getRoot() {
		return root;
	}
	public void setRoot(Root root) {
		this.root = root;
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
	public List<Is_A> getIndicators() {
		return indicators;
	}
	public void setIndicators(List<Is_A> indicators) {
		this.indicators = indicators;
	}
	

}

