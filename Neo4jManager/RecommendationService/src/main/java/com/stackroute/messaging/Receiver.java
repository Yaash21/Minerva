package com.stackroute.messaging;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.model.RecommendationModel;
import com.stackroute.model.UserInput;

import com.stackroute.service.RecommendService;

@Component
public class Receiver {
	
	@Autowired
	private RecommendService recommendation;
	@Autowired
	private UserInput userInput;
	@Autowired
	private Publisher publish;
	
	 public  void receiveMessage(String message) {
	        System.out.println("Received <" + message + ">");
			String fetchedMessage=message;
			
			Gson gson = new Gson();
			Type type = new TypeToken<UserInput>() {}.getType();
			
				userInput = gson.fromJson(fetchedMessage, type);
				String domain = userInput.getDomain();
				String concept = userInput.getConcept();
				String intent =userInput.getIntent();
				String sessionId = userInput.getSessionId();
				RecommendationModel recommendationModel	= recommendation.Recommendations(domain, concept, intent,sessionId);
					String json = gson.toJson(recommendationModel);
					try {
						publish.publishMsg(json);
					} catch (AmqpException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
					
	    }

}
