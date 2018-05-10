package com.stackroute.listener;

import java.io.IOException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.*;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.VideoCrawlerFinalApplication;

@Component
public class Producer {
	
	  AmqpTemplate amqpTemplate;
	 VideoCrawlerFinalApplication mainApplication = new VideoCrawlerFinalApplication();
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	    @Autowired    
	    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
	        this.amqpTemplate = amqpTemplate;
	    }


	    
	    public String produceMsg(JSONObject obj){
	    	
				amqpTemplate.convertAndSend(VideoCrawlerFinalApplication.publishQueue, String.valueOf(obj.toString()));
	        
				System.out.println("Send msg = "+  obj.toString());
				return obj.toString();
		
	    }
}
   
        
