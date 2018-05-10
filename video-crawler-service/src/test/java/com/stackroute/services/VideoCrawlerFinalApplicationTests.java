package com.stackroute.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.stackroute.domain.Domain;
import com.stackroute.listener.Producer;
import com.stackroute.service.VideoService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(VideoService.class)
public class VideoCrawlerFinalApplicationTests {
	
	private VideoService videoService;
	
	private Domain result;
	
	@Mock
	Producer publisher;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		videoService = new VideoService();
		videoService.setProducer(publisher);
		result = new Domain();
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		result.setUrls(urls);
		result.setConcept("2");
		result.setDomain("Java");
		result.setDate(null);
	}
	
	@Test
	public void codeCountTest() throws IOException {
		//Arrange
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		try {
			json.put("VideoCount", 0);
			json.put("url", "https://docs.oracle.com/javase/1.4.2/docs");	
//			json.put("concept", "2");
//			json.put("domain", "Java");	
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		jsonList.add(json);
		
		List<JSONObject> jsonListExpected = videoService.videoCount(result);
		//Assert
		
		System.out.println(jsonListExpected.toString());
		System.out.println(jsonList.toString());

		assertThat(jsonListExpected.toString(), is(equalTo(jsonList.toString())));
	}

}
