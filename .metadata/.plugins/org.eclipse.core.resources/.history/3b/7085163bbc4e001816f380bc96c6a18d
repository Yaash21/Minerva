package com.stackroute.service;


import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.amqp.AmqpException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.stackroute.domain.Domain;
import com.stackroute.messaging.Producer;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(Service.class)
public class ServiceTest {

	private Service service;

	private Domain domain;

	@Mock
	private Producer producer;



	@Before
	public void setupMock() throws AmqpException, IOException {
		MockitoAnnotations.initMocks(this);
		service= new Service();
		domain= new Domain();

		service.setProducer(producer);
	}

	@Test
	public void imgCountTest() throws IOException, JSONException, ParseException {
		//Arrange
		List<String> urls = new ArrayList<String>();
		urls.add("https://www.javatpoint.com");
		List<JSONObject> objList = new ArrayList<JSONObject>();
		JSONObject obj = new JSONObject();
		obj.put("ImgCount",134 );
		obj.put("url","https://www.javatpoint.com");
		objList.add(obj);
		domain.setUrls(urls);
		domain.setConcept("Website");
		domain.setDomain("Java");
		//		String pattern = "MM-dd-yyyy";
		//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		//		Date date = simpleDateFormat.parse("12-01-2018");
		domain.setDate(null);
		//Act
		List<JSONObject> objListExpected = service.imgCount(domain);
		//			String actual=obj.toString();
		//Assert
		//			String expected ="{\"ImgCount\":127,\"url\":\"https://www.javatpoint.com\"}";
		//	 assertEquals(actual,expected);
		String actual= objList.toString();
		String expected = objListExpected.toString();
		assertEquals(actual,expected);
	}

}
