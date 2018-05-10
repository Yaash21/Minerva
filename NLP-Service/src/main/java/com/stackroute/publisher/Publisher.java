package com.stackroute.publisher;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.NlpServiceApplication;


@Component
public class Publisher {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
		AmqpTemplate amqpTemplate;

		@Autowired	
		public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
			this.amqpTemplate = amqpTemplate;
		}

		@Autowired
		NlpServiceApplication nlpServiceApplication;

		public String produceMsgToSearchService(JSONObject json){ 

			amqpTemplate.convertAndSend(nlpServiceApplication.EXCHANGE, nlpServiceApplication.KEY, json.toString());
			//logger.info(json.get("url")+" "+json.get("codecount"));
			System.out.println("Published Successfully");
			return "Published Successfully";
		}
		
		public String produceMsgToDialogFlow(JSONObject json){ 

			amqpTemplate.convertAndSend(nlpServiceApplication.PUBLISH_QUEUE, json.toString());
			//logger.info(json.get("url")+" "+json.get("codecount"));
			System.out.println("Published Successfully");
			return "Published Successfully";
			
		}

}
