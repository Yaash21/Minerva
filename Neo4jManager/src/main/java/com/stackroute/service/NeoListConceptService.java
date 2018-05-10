package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.dataLoader.DataLoaderConcept;
import com.stackroute.redisson.Neo4jConceptModel;


@Service
public class NeoListConceptService {

	
	@Autowired
	private DataLoaderConcept dataLoader;

	public Neo4jConceptModel neo4jConceptList(){
		
		Neo4jConceptModel neoConceptModelFinal = dataLoader.getBucket().get();
		return neoConceptModelFinal;

}
}
