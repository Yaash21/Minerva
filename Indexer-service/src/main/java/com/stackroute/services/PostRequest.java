package com.stackroute.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.stackroute.domain.Confidence;

/**
 * notifies the changes in graph neo4j Manager through http get request
 * 
 */
@Service
public class PostRequest {

	public void postMethod(Confidence confidence, String redisUri) {
		// String ack = "{ \"Concept\":\"" + confidence.getConceptName() + "\",
		// \"Domain\":\"" + confidence.getDomainName()
		// + "\" }";
		String concept = confidence.getConceptName().replaceAll(" ", "%20");
		String rd = redisUri + "?concept=" + concept + "&domain=" + confidence.getDomainName();
		System.out.println("Url:" + rd);
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject(rd, String.class);

	}
}