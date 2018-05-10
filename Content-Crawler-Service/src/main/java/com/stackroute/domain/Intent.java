package com.stackroute.domain;

import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import lombok.Getter;
import lombok.Setter;

@NodeEntity
@Getter @Setter
public class Intent {
	
	@GraphId
	private int id;
	private String name;
	
	@Relationship(type="Is_A", direction = Relationship.INCOMING)	 
    private List<IsARelation> indicators;
	
	
	@Relationship(type="Is_An")
	private Root root;
	
}
