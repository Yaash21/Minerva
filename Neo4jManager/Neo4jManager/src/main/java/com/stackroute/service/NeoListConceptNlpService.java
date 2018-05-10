package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.dataLoader.DataLoaderConceptNlp;

import com.stackroute.redisson.Neo4jConceptNlpModel;


@Service
public class NeoListConceptNlpService {

	
	@Autowired
	private DataLoaderConceptNlp dataLoader;
	
public Neo4jConceptNlpModel neo4jConceptList(){
		
	Neo4jConceptNlpModel neoConceptNlpModelFinal = dataLoader.getBucket().get();
		return neoConceptNlpModelFinal;
	
}
}
