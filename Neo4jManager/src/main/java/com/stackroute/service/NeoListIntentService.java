package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.dataLoader.DataLoaderIntent;
import com.stackroute.redisson.Neo4jIntentModel;


@Service
public class NeoListIntentService {
	
	@Autowired
	private DataLoaderIntent dataLoader;
	
public Neo4jIntentModel neo4jIntentList(){
		
		Neo4jIntentModel neoIntentModelFinal = dataLoader.getBucket().get();
		return neoIntentModelFinal;
	
}
	

}
