package com.stackroute.listener;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.Result;
import com.stackroute.service.CodeOccuranceCounterService;

/**
 * Rabbit MQ Listener Class
 *
 */
@Component
public class MessageListener {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String message;
	
	CodeOccuranceCounterService codeOccuranceCounterService;
	
	@Autowired
	public void setCodeOccuranceCounterService(CodeOccuranceCounterService codeOccuranceCounterService) {
		this.codeOccuranceCounterService = codeOccuranceCounterService;
	}

    public String getMessage() {
		return message;
	}
       
    /**
     * message fetched from the rabbit mq
     * @param message
     */
    
    public void receiveMessage(String message) {
    	this.message = message;
    	Gson gson = new Gson();
        Type type = new TypeToken<Result>() {}.getType();
        Result result = gson.fromJson(message, type);
        logger.info("Message received"+message.toString());
        codeOccuranceCounterService.getCodeSnippetCount(result);
		
    }
}

