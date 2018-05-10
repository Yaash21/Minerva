package com.stackroute.receiver;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.InputQuery;
import com.stackroute.publisher.Publisher;
import com.stackroute.service.LemmatizerService;
import com.stackroute.service.SpellCheckService;

@Component
public class Receiver {

	@Autowired
	Publisher publisher;

	@Autowired
	LemmatizerService lemmatizerService;

	@Autowired
	SpellCheckService spellCheckService;

	public void receiveMessage(String message) {

		Gson gson = new Gson();
		Type type = new TypeToken<InputQuery>() {
		}.getType();
		InputQuery inputQuery = gson.fromJson(message, type);
		String queryAfterLemmatizing = lemmatizerService.lemmatize(inputQuery.getQuery());
		String queryAfterSpellChecks = spellCheckService.calculateDistance(queryAfterLemmatizing);
		String spell = spellCheckService.getSpelling();
		JSONObject json = new JSONObject();
		try {
			json.put("spelling", spell);
			json.put("query", queryAfterSpellChecks);
			json.put("sessionId", inputQuery.getSessionId());
			json.put("domain", inputQuery.getDomain());
			json.put("concept", inputQuery.getConcept());
			json.put("intent", inputQuery.getIntent());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		publisher.produceMsg(json);

	}

}
