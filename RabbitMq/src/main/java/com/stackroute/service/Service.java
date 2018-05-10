package com.stackroute.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Service {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${semantic.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${semantic.rabbitmq.routingkey}")
	private String routingkey;	
	String kafkaTopic = "java_in_use_topic";
	
	public void send() {
		amqpTemplate.convertAndSend(exchange, routingkey, " ");
		System.out.println("Working fine");
	    
	}

}
