package com.stackroute.rabbitmq.listener;

import java.lang.reflect.Type;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.domain.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.rabbitmq.publisher.Publisher;
import com.stackroute.service.TextCrawlerService;


@Component
public class MessageListener {
	
	private String message;
	
	TextCrawlerService crawlerService;
	
	@Autowired
	public void setCodeService(TextCrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}

    public String getMessage() {
		return message;
	}
    
    @Autowired
	Publisher publisher;
    
    
    public void receiveMessage(String message) {
    	this.message = message;
    	Gson gson = new Gson();
        Type type = new TypeToken<Result>() {}.getType();
        Result result = gson.fromJson(message, type);
       // logger.info("Message received"+message.toString());
    	System.out.println("Message received"+message.toString());
    	crawlerService.getAppearanceScore(result);
    }
}

