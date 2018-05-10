package com.stackroute.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.stackroute.domain.Result;
import com.stackroute.rabbitmq.publisher.Publisher;


@Service
public class TextCrawlerService {

	TermService termService;

	@Autowired 
	public void setTermService(TermService termService) {
		this.termService = termService;
	}

	@Autowired
	AmqpTemplate amqpTemplate;

	Publisher publisher;

	@Autowired
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public List<JSONObject> getAppearanceScore(Result result){
		JSONObject json = new JSONObject();
		JSONObject jsonOutput = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject weights =new JSONObject();
		List<JSONObject> jsonList = new ArrayList<JSONObject>();

		try {
			ClassPathResource classPathResource = new ClassPathResource("tag-weights.json");
			InputStream inputStream = classPathResource.getInputStream();
			File file = File.createTempFile("test", ".txt");
			try {
				FileUtils.copyInputStreamToFile(inputStream, file);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			FileReader fr = new FileReader(file);
			weights =  (JSONObject)parser.parse(fr);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> terms = termService.getTerms();

		String[] keys = {"title","meta[name=keywords]","meta[name=description]","h1","h2","h3","h4","h5","h6","p","li"};

		for(String url: result.getUrls()) {
			try {
				Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
				HashMap<String, HashMap<String, Integer>> appearanceMatrix = new HashMap<>();
				for(int i =0; i<keys.length; ++i) {
					String key = keys[i];
					Elements ele = doc.select(key);
					String[] words = ele.text().replaceAll("[^a-zA-Z0-9 ]","").toLowerCase().split("\\s+");
					if(i==1 || i==2) {
						if(ele.size() != 0)
							words = ele.get(0).attr("content").replaceAll("[^a-zA-Z0-9 ]","").toLowerCase().split("\\s+");
						else
							words = null;
					}

					HashMap<String, Integer> map = new HashMap<>();
					if(words!= null) {
						for(String word: words) {
							if(map.containsKey(word)) {
								int x = map.get(word);
								map.put(word, x+1);
							}
							else
								map.put(word, 1);
						}
					}

					for(String term: terms) {
						if(appearanceMatrix.containsKey(term)) {
							appearanceMatrix.get(term).put(key, (map.get(term)==null)?0:map.get(term));
						}
						else {
							HashMap<String, Integer> tagWiseFrequency = new HashMap<>();
							if(map.containsKey(term.toLowerCase())){
								tagWiseFrequency.put(key, map.get(term.toLowerCase()));
							}
							else {
								tagWiseFrequency.put(key, 0);
							}
							appearanceMatrix.put(term.toLowerCase(), tagWiseFrequency);
						}
					}

				}

				for(String term: appearanceMatrix.keySet()){
					HashMap<String, Integer> hmap = appearanceMatrix.get(term);
					long appScore = 0;
					for(String key: hmap.keySet()) {
						appScore += (hmap.get(key)*(long)weights.get(key));
					}
					json.put(term, appScore);
				}
				jsonOutput.put("url", url);
				jsonOutput.put("domain", result.getDomain());
				jsonOutput.put("concept", result.getConcept());
				jsonOutput.put("terms", json);
				publisher.produceMsg(jsonOutput);
				jsonList.add(jsonOutput);

			} 
			catch (IOException e) {
				System.out.println("Page Not Found");
				for(String term: terms){
					json.put(term, 0);
				}
				jsonOutput.put("url", url);
				jsonOutput.put("domain", result.getDomain());
				jsonOutput.put("concept", result.getConcept());
				jsonOutput.put("terms", json);
				publisher.produceMsg(jsonOutput);
			} 
		}
		return jsonList;
	}
}
