package com.stackroute.rabbitmq.publisher;

import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.ContentCrawlerServiceApplication;



@Component
public class Publisher {
	
	AmqpTemplate amqpTemplate;
	
	@Autowired	
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}
	
	public void produceMsg(JSONObject json){
		amqpTemplate.convertAndSend(ContentCrawlerServiceApplication.PUBLISH_QUEUE, String.valueOf(json.toString()));
		System.out.println("Published successfully");
	}

}
