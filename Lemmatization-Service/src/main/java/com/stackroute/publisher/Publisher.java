package com.stackroute.publisher;

import org.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.LemmatizationServiceApplication;

@Component
public class Publisher {

	AmqpTemplate amqpTemplate;

	@Autowired
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	@Autowired
	LemmatizationServiceApplication lemmatizationServiceApplication;

	public String produceMsg(JSONObject json) {

		amqpTemplate.convertAndSend(lemmatizationServiceApplication.PUBLISH_QUEUE, String.valueOf(json.toString()));
		return "Published Successfully";
	}

}
