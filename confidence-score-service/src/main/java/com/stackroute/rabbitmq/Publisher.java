package com.stackroute.rabbitmq;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.ConfidenceScoreCalculatorApplication;

@Component
public class Publisher {

	AmqpTemplate amqpTemplate;
	ConfidenceScoreCalculatorApplication mainApplication = new ConfidenceScoreCalculatorApplication();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired    
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}



	public String produceMsg(JSONObject obj){

		logger.info("This is an info message");
		amqpTemplate.convertAndSend(mainApplication.publishQueue, String.valueOf(obj.toString()));

		System.out.println("Send msg = "+  obj.toString());
		return obj.toString();

	}
}
