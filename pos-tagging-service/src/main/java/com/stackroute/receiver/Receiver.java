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
import com.stackroute.service.PosTaggingService;



@Component
public class Receiver {
	
	@Autowired
	PosTaggingService posTaggingService;
	
	@Autowired
	Publisher publisher;
	
	String fetchmessage = "";
	
	
	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		
		Gson gson = new Gson();
		Type type = new TypeToken<InputQuery>() {}.getType();
		fetchmessage = message;
		InputQuery inputQuery = gson.fromJson(message, type);
		
		String queryAfterPOSTagging = posTaggingService.posTagging(inputQuery.getQuery());
		
		System.out.println("before publishing " + queryAfterPOSTagging);
		
		JSONObject result = new JSONObject();
		try {
			result.put("spelling", inputQuery.getSpelling());
			result.put("query", queryAfterPOSTagging);
			result.put("sessionId", inputQuery.getSessionId());
			result.put("domain", inputQuery.getDomain());
			result.put("concept", inputQuery.getConcept());
			result.put("intent", inputQuery.getIntent());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		publisher.produceMsg(result);
	}
	public String getMessage(){
		return this.fetchmessage;
	}

}
