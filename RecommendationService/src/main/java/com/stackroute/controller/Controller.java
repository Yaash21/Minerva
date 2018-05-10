package com.stackroute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.model.RecommendationModel;
import com.stackroute.service.RecommendService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class Controller {

	@Autowired
	RecommendService recommendService;



	@GetMapping(value = "/user", produces = "application/json")
	public ResponseEntity <RecommendationModel> addUser(@RequestParam("domain") final String domain,
			@RequestParam("concept") final String concept, @RequestParam("intent") final String intent,@RequestParam("sessionId") final String sessionId) {

		//		String Query = "Match(n:url)-[x:" + userInput.getIntent() + "]->(c:concept{name:\"" + userInput.getConcept()
		//		+ "\""
		//		+ "})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,
		//		n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator
		//		as counterIndicator, x.confidenceScore as confidenceScore";

		RecommendationModel recommendationModel = recommendService.Recommendations(domain, concept, intent,sessionId);
		System.out.println(recommendationModel.toString());
		return new ResponseEntity<RecommendationModel>( recommendationModel, HttpStatus.CREATED);


	}




}
