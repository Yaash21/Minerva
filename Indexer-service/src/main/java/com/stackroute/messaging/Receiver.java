package com.stackroute.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.services.FetchingService;

@Component
public class Receiver {

	@Autowired
	private FetchingService service;

	String fetchedMessage;

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		fetchedMessage = message;

		service.fetch(message);

	}

	public String getMessage() {
		return this.fetchedMessage;
	}
}
