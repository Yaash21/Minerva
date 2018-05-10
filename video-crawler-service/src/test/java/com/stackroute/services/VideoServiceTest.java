//package com.stackroute.services;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.amqp.AmqpException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import com.stackroute.domain.Domain;
//import com.stackroute.listener.Producer;
//import com.stackroute.listener.Receiver;
//import com.stackroute.service.VideoService;
//
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(VideoService.class)
//public class VideoServiceTest {
//	
//	private VideoService videoService;
//	Domain domain=new Domain();
//	
//	
//	@Mock
//	private Producer producer;
//	
//	@Mock
//	private Receiver receiver;
//	
//	@Before
//	public void setupMock() throws AmqpException, IOException {
//		MockitoAnnotations.initMocks(this);
//		videoService = new VideoService();
//		videoService.setProducer(producer);
//	       List<String> urls = new ArrayList<String>();
//	       
//	       urls.add("https://angularfirebase.com/lessons/installable-angular-progressive-web-app/");
//	       domain.setUrls(urls);
//	       domain.setConcept("2");
//	       domain.setDomain("Java");
//	       domain.setDate(null);
//	}
//	
//	@Test
//	public void videoCountTest() throws IOException {
//		//Arrange
//		List<JSONObject> objList = new ArrayList<JSONObject>();
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("VideoCount",0 );
//			obj.put("url","https://angularfirebase.com/lessons/installable-angular-progressive-web-app/");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		objList.add(obj);
//		//Act
//		 List<JSONObject> objListExpected = videoService.videoCount(domain);
////			String actual=obj.toString();
//		//Assert
////			String expected ="{\"ImgCount\":127,\"url\":\"https://www.javatpoint.com\"}";
////	 assertEquals(actual,expected);
//		 System.out.println(objListExpected+""+objList);
//	 assertEquals(objListExpected.toString(), objList.toString());
//	}
//
//}
