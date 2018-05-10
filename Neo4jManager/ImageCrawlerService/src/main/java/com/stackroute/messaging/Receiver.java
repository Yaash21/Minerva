package com.stackroute.messaging;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.Domain;
import com.stackroute.service.Service;

@Component
public class Receiver {
	
	@Autowired
	private Service service;
@Autowired
private Domain incomingMessage;
	
	String fetchedMessage;
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	    public  void receiveMessage(String message) {
	        System.out.println("Received <" + message + ">");
			fetchedMessage=message;
			logger.info("Message Recieved from Rabbit Mq");
			
			Gson gson = new Gson();
			Type type = new TypeToken<Domain>() {}.getType();
			
				incomingMessage = gson.fromJson(fetchedMessage, type);
						service.imgCount(incomingMessage );
					
			
	    }
		
			public String getMessage(){
				return this.fetchedMessage;

	}
}

