package com.stackroute.messaging;

import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.stackroute.service.Service;

@Component
public class Receiver {
	@Autowired
	private Service service;
	
	private JSONObject fetchedObj;
	private String fetchedMessage;
	
	public  void receiveMessage(String message) {
	
        System.out.println("Received <" + message + ">");
		 fetchedMessage=message;
		
		 try {
			fetchedObj = new JSONObject(fetchedMessage);
		
//		System.out.println(fetchedObj);
		
		service.integration(fetchedObj);
		 } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public String getFetchedMessage() {
		return fetchedMessage;
	}
	public JSONObject getFetchedObj() {
		return fetchedObj;
	}


}
