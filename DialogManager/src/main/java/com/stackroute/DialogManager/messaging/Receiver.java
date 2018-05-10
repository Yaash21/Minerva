package com.stackroute.DialogManager.messaging;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.DialogManager.controller.WebSocketController;
import com.stackroute.DialogManager.domain.SpellCheck;

@Component
public class Receiver {

	@Autowired
	private WebSocketController webSocketController;
	@Autowired
	private SpellCheck incomingMessage;

	public SpellCheck getIncomingMessage() {
		return incomingMessage;
	}

	String fetchedMessage;
	String fetchedMessage1;
	String fetchedMessage2;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void receiveMessage(String message) {

		fetchedMessage = message;
		logger.info("Message Recieved from Rabbit Mq");

		Gson gson = new Gson();
		Type type = new TypeToken<SpellCheck>() {
		}.getType();

		this.incomingMessage = gson.fromJson(fetchedMessage, type);
		webSocketController.onReceivedMessage();

	}

	public void receiveMessage1(String message) {

		fetchedMessage1 = message;
		logger.info("Message Recieved from Rabbit Mq");
		webSocketController.onReceivedMessage1();
	}

	public void receiveMessage2(String message) {

		fetchedMessage2 = message;
		logger.info("Message Recieved from Rabbit Mq");
		webSocketController.onReceivedMessage2();
	}

	public SpellCheck getMessage() {
		return this.getIncomingMessage();
	}

	public String getMessage1() {
		return this.fetchedMessage1;
	}

	public String getMessage2() {
		return this.fetchedMessage2;
	}
}
