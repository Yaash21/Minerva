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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.domain.Domain;
import com.stackroute.listener.Producer;

@Component
public class VideoService {

	private Producer producer;

	@Autowired
	public void setProducer(Producer producer) {
		this.producer = producer;
	}


	public List<JSONObject> videoCount(Domain incomingMessage){

		JSONObject obj = new JSONObject();
		List<JSONObject> objList = new ArrayList<JSONObject>();
		List<String> url = incomingMessage.getUrls();

		for(int i =0 ;i<url.size();i++){
			int count=0;
			int count1=0;
			JSONObject jsonObj = new JSONObject();
			Document doc;
			try {
				doc = Jsoup.connect(url.get(i)).userAgent("Mozilla/5.0").get();
				Elements videosFrame = doc.getElementsByTag("iframe");
				Elements videos = doc.getElementsByTag("video");
				for (Element video : videos) {
					count++;
				}  

				for (Element video : videosFrame) {
					count1++;
				}
				jsonObj.put("VideoCount", count+count1);
				jsonObj.put("url", url.get(i));
				jsonObj.put("concept",incomingMessage.getConcept());
				jsonObj.put("domain",incomingMessage.getDomain());
				obj.put("VideoCount",count+count1);
				obj.put("url", url.get(i));
				objList.add(obj);
				producer.produceMsg(jsonObj);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Page not found");
				try {
					jsonObj.put("VideoCount", -1);
					jsonObj.put("url", url.get(i));
					jsonObj.put("concept",incomingMessage.getConcept());
					jsonObj.put("domain",incomingMessage.getDomain());
					obj.put("VideoCount",count+count1);
					obj.put("url", url.get(i));
					objList.add(obj);
					producer.produceMsg(jsonObj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//e1.printStackTrace();
			}  
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		return objList;
	}  
}