package com.stackroute.publisher;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.PosTaggingServiceApplication;

@Component
public class Publisher {
	  AmqpTemplate amqpTemplate;
	  
	  @Autowired
	  PosTaggingServiceApplication posTaggingServiceApplication;
	  
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	    @Autowired    
	    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
	        this.amqpTemplate = amqpTemplate;
	    }


	    
	    public String produceMsg(JSONObject query){
	    	
	    	    logger.info("This is an info message");
				amqpTemplate.convertAndSend(posTaggingServiceApplication.PUBLISH_QUEUE, String.valueOf(query.toString()));
	        
				System.out.println("Send msg = "+  query.toString());
				return query.toString();
		
	    }

}
