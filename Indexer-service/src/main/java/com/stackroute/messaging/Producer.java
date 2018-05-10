package com.stackroute.messaging;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.IndexerServiceApplication;

@Component
public class Producer {

	AmqpTemplate amqpTemplate;

	@Autowired
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	public void produceMsg(JSONObject jsonObj) throws AmqpException, IOException {

		amqpTemplate.convertAndSend(IndexerServiceApplication.publishQueue, jsonObj.toString());
		System.out.println("Send msg = " + jsonObj.toString());

	}

}