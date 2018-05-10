package com.stackroute.service;

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
import org.mockito.junit.MockitoJUnitRunner;

import com.stackroute.domain.Result;
import com.stackroute.publisher.Publisher;

@RunWith(MockitoJUnitRunner.class)
public class CodeOccuranceCounterServiceTest {

	private CodeOccuranceCounterService codeOccuranceCounterService;
	
	private Result result;
	
	@Mock
	Publisher publisher;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		codeOccuranceCounterService = new CodeOccuranceCounterService();
		codeOccuranceCounterService.setPublisher(publisher);
		result = new Result();
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		result.setUrls(urls);
		result.setConcept("2");
		result.setDomain("Java");
		result.setDate(null);
	}
	
	@Test
	public void codeCountTest() throws IOException, JSONException {
		//Arrange
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		json.put("codecount", 1);
		json.put("url", "https://docs.oracle.com/javase/1.4.2/docs");	
		json.put("concept", "2");
		json.put("domain", "Java");
		
		jsonList.add(json);
		
		List<JSONObject> jsonListExpected = codeOccuranceCounterService.getCodeSnippetCount(result);
		//Assert
		
//		System.out.println(jsonListExpected.toString());
//		System.out.println(jsonList.toString());

		assertEquals(jsonListExpected.toString(), jsonList.toString());
	}

}
