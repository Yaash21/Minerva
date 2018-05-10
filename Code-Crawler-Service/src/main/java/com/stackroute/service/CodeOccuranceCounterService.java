package com.stackroute.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.domain.Result;
import com.stackroute.publisher.Publisher;

@Service
/**
 * CodeOccuranceCounterService class methods are defined here.
 * 
 * @author
 *
 */
public class CodeOccuranceCounterService {
	
	private Publisher publisher;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<JSONObject> getCodeSnippetCount(Result result){
		
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		try {	
			List<String> urls = result.getUrls();
			for(String url : urls){
				JSONObject json = new JSONObject();
				json.put("codecount", getCount(url));
				json.put("url", url);	
				json.put("concept", result.getConcept());
				json.put("domain", result.getDomain());	
				publisher.produceMsg(json);	
				jsonList.add(json);
			}
			return jsonList;
		}  
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonList;

	}
	
	public int getCount(String url){	

		int preCount = 0;
			try {
				Elements img;
				Document doc;
				doc = Jsoup.connect(url).userAgent("Mozilla").get();
				if (doc.select("pre").select("code").size() > 0){
					img = doc.select("pre").select("code");
				}
				else{
					img = doc.select("pre,code");
					
				}
				for(Element pres : img){
					preCount++;
				}
				return preCount;
				
			} 
			catch (IOException e) {
				logger.error("Page Not Found");
			}
			
			
			
		return -1;
		
	}
	
}

