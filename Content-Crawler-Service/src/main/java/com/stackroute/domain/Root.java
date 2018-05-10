package com.stackroute.domain;

import java.util.List;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Getter;
import lombok.Setter;

@NodeEntity
@Getter @Setter
public class Root {
	
	@GraphId
	private int id;
	private String name;
	
	@Relationship(type="Is_An", direction = Relationship.INCOMING)	 
    private List<IsAnRelation> intents;
	
}

