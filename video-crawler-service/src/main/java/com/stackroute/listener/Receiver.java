package com.stackroute.listener;

import java.io.IOException;
import java.lang.reflect.Type;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.Domain;
import com.stackroute.service.VideoService;

@Component
public class Receiver {
	
	@Autowired
	private VideoService service;
	String fetchedMessage;
	
	@Autowired
	Domain incomingMessage;
	
	    public void receiveMessage(String message) {
	        System.out.println("Received <" + message + ">");
			fetchedMessage=message;
			
			Gson gson=new Gson();
			Type type = new TypeToken<Domain>() {}.getType();
			incomingMessage = gson.fromJson(fetchedMessage, type);
				
					service.videoCount(incomingMessage);
			
	    }
		
			public String getMessage(){
				return this.fetchedMessage;

	}
}
