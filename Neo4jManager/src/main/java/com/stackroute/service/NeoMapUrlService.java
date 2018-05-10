package com.stackroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.stackroute.dataLoader.DataLoaderUrl;

import com.stackroute.redisson.Neo4jUrlModel;

@Service
public class NeoMapUrlService {
	
	@Autowired
	private DataLoaderUrl dataLoader;
	
public Neo4jUrlModel neo4jUrlMap(){
		
	Neo4jUrlModel neoUrlModelFinal = dataLoader.getBucket().get();
		return neoUrlModelFinal;

}
}
