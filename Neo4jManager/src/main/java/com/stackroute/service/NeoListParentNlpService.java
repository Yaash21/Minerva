package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.stackroute.dataLoader.DataLoaderParentNlp;

import com.stackroute.redisson.Neo4jParentNlpModel;

@Service
public class NeoListParentNlpService {

	
	@Autowired
	private DataLoaderParentNlp dataLoader;

	public Neo4jParentNlpModel neo4jParentList(){
		
		Neo4jParentNlpModel neoParentModelFinal = dataLoader.getBucket().get();
		return neoParentModelFinal;
	}
}
