package com.stackroute.receiver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.InputQuery;
import com.stackroute.publisher.Publisher;
import com.stackroute.service.NlpService;


@Component
public class Receiver {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	Publisher publisher;

	
	NlpService nlpService;
	
	@Autowired
    public void setNlpService(NlpService nlpService) {
		this.nlpService = nlpService;
	} 
    
    public void receiveMessage(String message) {
    	
    	System.out.println("Received message "+message);
    	Gson gson = new Gson();
    	Type type = new TypeToken<InputQuery>() {}.getType();
    	InputQuery inputQuery = gson.fromJson(message, type);
    	String[] list = inputQuery.getQuery().split("\\s+");
    	List<String> queryList = new ArrayList<String>();
    	
    	for(String word:list)
    	{
    		queryList.add(word);
    	}
    	
    	System.out.println(queryList.toString());
    	
    	JSONObject fromService = nlpService.getConceptAndIntent(queryList, inputQuery);
    	System.out.println("From service"+fromService);
		
    }

}
