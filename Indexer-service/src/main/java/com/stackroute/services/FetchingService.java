package com.stackroute.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.stackroute.domain.Confidence;

/**
 * spring service class used for value mapping required for neo4j database,
 * calls executing service to perform neo4j query to populate domain-concept
 * graph
 * 
 */
@Service
public class FetchingService {
	@Autowired
	Gson gson;
	@Value("${uriofneo}")
	private String neo4juri;
	@Value("${spring.data.neo4j.username}")
	private String username;
	@Value("${spring.data.neo4j.password}")
	private String password;
	@Value("${redisUri}")
	private String redisUri;

	public void fetch(String json) {
		Confidence confidence = gson.fromJson(json, Confidence.class);

		ExecutingService.executeQuery(confidence, neo4juri, username, password, redisUri);

	}
}
