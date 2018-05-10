package com.stackroute.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.publisher.Publisher;
import com.stackroute.service.LemmatizerService;
import com.stackroute.service.SpellCheckService;

@RestController
@RequestMapping(value = "/api/v1")
/**
 * 
 * @author vaishnavi
 *
 */
public class LemmatizerController {

	@Autowired
	LemmatizerService lemmatizerService;

	@Autowired
	SpellCheckService levelstienDistance;

	@Autowired
	Publisher publisher;

	// @GetMapping("/concepts")
	// public List<String> concepts(){
	// return conceptService.fetchConcepts();
	// }
	//
	// @GetMapping("/intents")
	// public List<String> intents(){
	// return conceptService.fetchIntents();
	// }

	@PostMapping(value = "/pipeline")
	public ResponseEntity<String> fetchDomainConcepts(@RequestBody final String query) {

		String queryAfterLemmatizing = lemmatizerService.lemmatize(query);

		System.out.println("after lemmatize" + queryAfterLemmatizing);

		// System.out.println(levelstienDistance.calculateDistance(str1).toString());
		String queryAfterSpellChecks = levelstienDistance.calculateDistance(queryAfterLemmatizing);
		String spell = levelstienDistance.getSpelling();
		JSONObject json = new JSONObject();
		try {
			json.put("spelling", spell);
			System.out.println(queryAfterSpellChecks);
			json.put("query", queryAfterSpellChecks);
			json.put("sessionId", "lkft3e4l");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		publisher.produceMsg(json);
		return new ResponseEntity<String>(queryAfterSpellChecks, HttpStatus.CREATED);

	}

}
