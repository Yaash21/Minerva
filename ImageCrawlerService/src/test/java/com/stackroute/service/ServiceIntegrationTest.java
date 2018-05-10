//package com.stackroute.service;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
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
//import org.springframework.amqp.AmqpException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.stackroute.domain.Domain;
//import com.stackroute.messaging.Producer;
//import com.stackroute.messaging.Receiver;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public class ServiceIntegrationTest {
//
//	@Autowired
//	private Receiver receive ;
//	@Autowired
//	private Domain domain;
//	@Autowired
//	private Service service;
//	@Mock
//	private Producer producer;
//	
//	@Before
//	public void setupMock() throws AmqpException, IOException {
//		MockitoAnnotations.initMocks(this);
//		service.setProducer(producer);
//	}
//	
//	@Test
//	public void  testImageCount() throws IOException{
////		Arrange
//		receive.receiveMessage("{\"domain\":\"Java\",\"concept\":\"Algorithm\",\"date\":\"Mar 23, 2018 12:33:51 PM\",\"urls\":[\"https://www.javatpoint.com\"]}");
//		Gson gson = new Gson();
//		Type type = new TypeToken<Domain>() {}.getType();
//		
//			domain = gson.fromJson(receive.getMessage(), type);
//			
//		JSONObject obj = new JSONObject();
//		try {
//			obj.put("ImgCount",136 );
//			obj.put("url","https://www.javatpoint.com");
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		List<JSONObject> objActual = new ArrayList<JSONObject>() ;
//		objActual.add(obj);
////		Act
//		List<JSONObject> objExpected = service.imgCount(domain);
//		String actual = objActual.get(0).toString();
//		String expected=objExpected.get(0).toString();
//	
////		Assert
//		assertEquals(expected,actual);
//		
//		
//		
//	}
//	
//
//}
