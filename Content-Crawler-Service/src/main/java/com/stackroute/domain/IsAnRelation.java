package com.stackroute.domain;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import lombok.Getter;
import lombok.Setter;

@RelationshipEntity(type="Is_An")
@Getter @Setter
public class IsAnRelation {
	
	@StartNode
	private Intent intent;
	
	@EndNode
	private Root root;
	
}

