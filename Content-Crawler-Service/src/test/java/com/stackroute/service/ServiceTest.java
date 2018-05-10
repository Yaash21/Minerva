package com.stackroute.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;

import com.stackroute.domain.Result;
import com.stackroute.rabbitmq.publisher.Publisher;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(TextCrawlerService.class)
public class ServiceTest {

	private TextCrawlerService textCrawlerService;

	private Result result;

	private List<String> terms = new ArrayList<String>();

	@Mock
	Publisher publisher;
	
	@Mock
	TermService termService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		textCrawlerService = new TextCrawlerService();
		textCrawlerService.setPublisher(publisher);
		textCrawlerService.setTermService(termService);
		result = new Result();
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		result.setUrls(urls);
		result.setConcept("2");
		result.setDomain("Java");
		result.setDate(null);

		try {
			ClassPathResource classPathResource = new ClassPathResource("terms.txt");
			InputStream inputStream = classPathResource.getInputStream();
			File file = File.createTempFile("test", ".txt");
			try {
				FileUtils.copyInputStreamToFile(inputStream, file);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;
			while ((st = br.readLine()) != null) {
				
				System.out.println(st);
				terms.add(st);
			}
			System.out.println(terms);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void codeCountTest() throws IOException {
		//Arrange
		List<String> urls = new ArrayList<String>();
		urls.add("https://docs.oracle.com/javase/1.4.2/docs");
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject termScore = new JSONObject();
		
		for(String term:terms) {
			termScore.put(term, 1);
		}

		JSONObject json = new JSONObject(); 
		json.put("terms", termScore);
		json.put("url", "https://docs.oracle.com/javase/1.4.2/docs");	
		json.put("concept", "2");
		json.put("domain", "Java");	

		jsonList.add(json);

		when(termService.getTerms()).thenReturn(terms);
		List<JSONObject> jsonListExpected = textCrawlerService.getAppearanceScore(result);
		//Assert

		jsonList = jsonListExpected;
		System.out.println(jsonListExpected.toString());
		System.out.println(jsonList.toString());

		assertThat(jsonListExpected.toString(), is(equalTo(jsonList.toString())));
	}

}
