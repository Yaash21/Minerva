//package org.stackroute.service;
//
//import static org.junit.Assert.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
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
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import com.stackroute.messaging.Publish;
//import com.stackroute.model.Model;
//import com.stackroute.service.Service;
//
//@RunWith(MockitoJUnitRunner.class)
//@WebMvcTest(Service.class)
//public class ServiceTest {
//
//
//	private Service service;
//
//	private Model model;
//
//	@Mock
//	private Publish publish;
//
//	@Before
//	public void setupMock() throws AmqpException, IOException {
//		MockitoAnnotations.initMocks(this);
//		service= new Service();
//		model=new Model();
//
//		service.setPublish(publish);
//	}
//
//	@Test
//	public void integrationTest() {
//		//Arrange
//		JSONObject obj1 = new JSONObject();
//		JSONObject obj2 = new JSONObject();
//		JSONObject obj3 = new JSONObject();
//		JSONObject obj4 = new JSONObject();
//		JSONObject terms = new JSONObject();
//		JSONObject expected = new JSONObject();
//
//
//		try {
//			
//			terms.put("Delete",0);
//
//			terms.put("fundamental", 1);
//			obj1.put("terms", terms);
//			obj1.put("domain", "Java");
//			obj1.put("concept", "core");
//			obj1.put("url","https://www.javatpoint.com");
//
//			obj2.put("ImgCount",128);
//			obj2.put("domain", "Java");
//			obj2.put("concept", "core");
//			obj2.put("url","https://www.javatpoint.com");
//
//			obj3.put("VideoCount",2);
//			obj3.put("domain", "Java");
//			obj3.put("concept", "core");
//			obj3.put("url","https://www.javatpoint.com");
//
//			obj4.put("codecount",5);
//			obj4.put("domain", "Java");
//			obj4.put("concept", "core");
//			obj4.put("url","https://www.javatpoint.com");
//
//			//Act
//
//			service.integration(obj1);
//
//			service.integration(obj2);
//			
//			service.integration(obj3);
//			ArrayList<JSONObject> actual = service.integration(obj4);
//
//			//Assert
//			expected.put("concept", "core");
//			expected.put("domain", "Java");
//			
//			expected.put("url","https://www.javatpoint.com");
//			expected.put("imgCount", 128);
//			expected.put("codeCount", 5);
//			expected.put("videoCount", 2);
//			expected.put("terms", terms);
//			expected.put("titleUrl","Tutorials - Javatpoint");
//			expected.put("metaUrl","Tutorials, Free Online Tutorials, Javatpoint provides tutorials and interview questions of all technology like java tutorial, android, java frameworks, javascript, ajax, core java, sql, python, php, c language etc. for beginners and professionals.");
//			
//			
//			
//			String expectedString = expected.toString();
//			String actualString = actual.get(0).toString();
//			System.out.println(expectedString);
//			System.out.println(actualString);
//			assertEquals(actualString,expectedString);
//			
//
//
//
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//
//
//	}
//}
