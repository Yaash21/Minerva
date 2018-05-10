package com.stackroute.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
/**
 * This GoogleApiSearchService is calling google custom search api and fetching 
 * list of urls
 * 
 * @author 
 *
 */
public class GoogleApiSearchService{

	@Value("${apikey}")
	private String apiKey;
	
	@Value("${customeSearchEngineKey}")
	private String customSearchEngineKey;

	@Value("${searchURL}")
	private String searchURL; 
	
	@Value("${numberOfUrls}")
	int numberOfUrls;
	
	@Value("${numberOfRuns}")
	int numberOfRuns;
	
	@Value("${startingPoint}")
	int startPoint;
	
	/**
	 * 
	 * @param domain
	 * @param concept
	 * @return
	 */
	public List<String> getUrls(String domain, String concept) {
		int runningNumber = 0;
		List<String> urls = new ArrayList<String>();
		
		while(runningNumber < numberOfRuns) {
			runningNumber++;
			try {
				String toSearch = searchURL + "key=" + apiKey + "&cx=" + customSearchEngineKey + "&q=";
				toSearch += (domain.trim().replaceAll("\\s+", "%20")+'+'+concept.trim().replaceAll("\\s+", "%20"));
				toSearch += "&alt=json";
				toSearch += "&start=" + startPoint;
				toSearch += "&num=" + numberOfUrls;
				
				URL url = new URL(toSearch);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer buffer = new StringBuffer();
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				JsonParser parser = Json.createParser(new StringReader(buffer.toString()));
				
				while (parser.hasNext()) {
					Event event = parser.next();
					if(event == Event.KEY_NAME) {
						if (parser.getString().equals("link")) {
							Event value = parser.next();
							if (value == Event.VALUE_STRING) {
								urls.add(parser.getString());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
			startPoint += numberOfUrls;
		}
		return urls;
	}
}