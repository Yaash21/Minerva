package com.stackroute.DialogManager.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	AmqpTemplate amqpTemplate;

	@Autowired
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	public void produceMsg(String searchResult) {
		amqpTemplate.convertAndSend("dialogflow-service-queue", searchResult);
	}

}
