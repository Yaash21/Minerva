package com.stackroute.messaging;

import java.io.IOException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.ImageCrawlerServiceApplication;
import com.stackroute.service.Service;

@Component
public class Producer {
	
	
	
	  AmqpTemplate amqpTemplate;
	  @Autowired
	 ImageCrawlerServiceApplication mainApplication;
	    
	    @Autowired    
	    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
	        this.amqpTemplate = amqpTemplate;
	    }

	    private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	    
	    public void produceMsg(JSONObject jsonObj) throws AmqpException, IOException{
	
				amqpTemplate.convertAndSend(mainApplication.publishQueue, jsonObj.toString());        
				System.out.println("Send msg = "+  jsonObj.toString());
				logger.info("Message Successfully published");
	
		
	    }
   
        
}

