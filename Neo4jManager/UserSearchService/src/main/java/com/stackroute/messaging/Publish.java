package com.stackroute.messaging;

import java.io.IOException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.UserSearchServiceApplication;


@Component
public class Publish {
	
	AmqpTemplate amqpTemplate;
	  @Autowired
	  UserSearchServiceApplication mainApplication;
	  
	  @Autowired    
	    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
	        this.amqpTemplate = amqpTemplate;
	    }
	  
	  public void publishMsg(String json) throws AmqpException, IOException{
			
			amqpTemplate.convertAndSend(mainApplication.publishQueue, json);        
			System.out.println("Send msg = "+  json);

	
  }

}
