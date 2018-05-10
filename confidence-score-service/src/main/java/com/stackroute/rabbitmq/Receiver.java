package com.stackroute.rabbitmq;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.IncomingFormat;
import com.stackroute.service.ConfidenceScoreService;
import com.stackroute.service.MainService;
@Component
public class Receiver {



	@Autowired
	IncomingFormat incomingFormat;

	@Autowired
	ConfidenceScoreService confidenceScoreCalculator;
	
	@Autowired
	MainService mainService;
	String fetchedMessage;



	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		fetchedMessage = message;

		Gson gson=new Gson();
		Type type = new TypeToken<IncomingFormat>() {}.getType();
		incomingFormat = gson.fromJson(fetchedMessage, type);
		mainService.getConfidenceScore(incomingFormat);
		

	}


	public String getMessage(){
		return this.fetchedMessage;

	}

}
