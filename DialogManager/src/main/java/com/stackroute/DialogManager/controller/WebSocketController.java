package com.stackroute.DialogManager.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.stackroute.DialogManager.api.AIServiceException;
import com.stackroute.DialogManager.domain.Message;
import com.stackroute.DialogManager.domain.QueryDetails;
import com.stackroute.DialogManager.domain.RecommendationMessage;
import com.stackroute.DialogManager.domain.Recommendations;
import com.stackroute.DialogManager.domain.SpellCheck;
import com.stackroute.DialogManager.domain.UrlDetails;
import com.stackroute.DialogManager.messaging.Producer;
import com.stackroute.DialogManager.messaging.Receiver;
import com.stackroute.DialogManager.service.TextClientService;

@Controller
public class WebSocketController {

	@Autowired
	Producer producer;
	@Autowired
	Receiver receiver;
	@Autowired
	Gson gson;

	private final SimpMessagingTemplate template;
	private TextClientService textClientService;
	private LinkedHashMap<String, TextClientService> map = new LinkedHashMap<>();

	@Autowired
	public WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/send/message")
	public void onReceivedMessage(Message message) {

		if (map.containsKey(message.getSessionId())) {
			textClientService = map.get(message.getSessionId());
		} else {
			map.put(message.getSessionId(), new TextClientService());
			textClientService = map.get(message.getSessionId());
		}

		QueryDetails queryDetails = textClientService.responseEmmitter(message.getMessage(), message.getSessionId());
		String responseMessage = queryDetails.getResponseMessage();
		this.template.convertAndSend("/chat/" + message.getSessionId(), responseMessage);
		String jsonString = gson.toJson(queryDetails);

		if (queryDetails.getResponseMessage().equals("I can help you with that"))
			producer.produceMsg(jsonString);
		else if (queryDetails.getResponseMessage().equals("I could not understand what you are looking for")) {
			producer.produceMsg(jsonString);

		}

	}

	@MessageMapping("/send/recommendation")
	public void toPipeline(RecommendationMessage message) {

		QueryDetails queryDetails = new QueryDetails();
		queryDetails.setQuery(message.getMessage());
		queryDetails.setSessionId(message.getSessionId());
		queryDetails.setIntent(message.getIntent());

		String jsonString = gson.toJson(queryDetails);
		producer.produceMsg(jsonString);

	}

	public void onReceivedMessage1() {

		if (receiver.getMessage1() != null) {
			UrlDetails urlDetails = gson.fromJson(receiver.getMessage1(), UrlDetails.class);
			this.template.convertAndSend("/search/" + urlDetails.getSessionId(), this.receiver.getMessage1());
		}
	}

	public void onReceivedMessage() {
		SpellCheck spellCheck = new SpellCheck();
		spellCheck = receiver.getMessage();

		if (spellCheck != null) {
			if (spellCheck.getType().equals("CYIY")) {
				textClientService = this.map.get(spellCheck.getSessionId());
				try {
					textClientService.getDataService().resetActiveContexts();
					textClientService.setContext(2);

				} catch (AIServiceException e) {
					e.printStackTrace();
				}

				if (spellCheck.getSpelling().equals("wrong")) {
					this.template.convertAndSend("/chat/" + spellCheck.getSessionId(), spellCheck.getMessage());
				}
			} else if (!spellCheck.getParentNodes().isEmpty()) {
				this.template.convertAndSend("/chat/" + spellCheck.getSessionId(), spellCheck.getParentNodes());
				spellCheck = null;
			}
		}
	}

	public void onReceivedMessage2() {

		if (receiver.getMessage2() != null) {
			Recommendations recommendations = gson.fromJson(receiver.getMessage2(), Recommendations.class);
			this.template.convertAndSend("/recommendation/" + recommendations.getSessionId(),
					this.receiver.getMessage2());
		}
	}

}
