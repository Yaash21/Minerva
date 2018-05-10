package com.stackroute.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.stackroute.domain.IncomingFormat;
import com.stackroute.domain.NeoFetch;
import com.stackroute.rabbitmq.Publisher;
import org.json.simple.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class MainServiceTest {
	
	private MainService mainService;
	
	@Mock
	private ConfidenceScoreService confidenceScoreService;
	
	@Mock
	private Publisher publisher;
	
	IncomingFormat incomingFormat = new IncomingFormat();

	List<NeoFetch> neoFetchList = new ArrayList<>();
	JSONObject terms = new JSONObject();
	
	  @Before
	  public void setUp() throws Exception {
		  mainService = new MainService();
		  mainService.setConfidenceScoreService(confidenceScoreService);
		  mainService.setPublisher(publisher);
		  terms.put("define", 3.0);
		  terms.put("how", 10.0);
		  incomingFormat.setDomain("Java");
		  incomingFormat.setConcept("Spring");
		  incomingFormat.setCodeCount(2);
		  incomingFormat.setTerms(terms);
		  incomingFormat.setUrl("https://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/overview.html");
		  incomingFormat.setVideoCount(1);
		  incomingFormat.setMetaUrl("");
		  incomingFormat.setTitleUrl("");
		  
		  NeoFetch neoFetch1 = new NeoFetch();
		  NeoFetch neoFetch2 = new NeoFetch();
	        neoFetch1.setIntent("Beginner");
	        neoFetch1.setName("define");
	        neoFetch1.setWeight("7");
	        
	        neoFetch2.setIntent("Intermediate");
	        neoFetch2.setName("classify");
	        neoFetch2.setWeight("7");
	        
	        neoFetchList.add(neoFetch1);
	        neoFetchList.add(neoFetch2);
	  }

	@Test
	public void testMainService() {
		
		when(confidenceScoreService.graph()).thenReturn(neoFetchList);
		List<JSONObject> actualData = mainService.getConfidenceScore(incomingFormat);
		String actualResult = "";
		String expectedResult = "";
		
		for(JSONObject obj:actualData){
//			System.out.println(obj.toString());
			actualResult+=obj.toString()+", ";
		}
		
		actualResult = actualResult.substring(0, actualResult.length()-2);
		actualResult =  "["+actualResult+"]";
		
		expectedResult = actualData.toString();
		
//		String expectedResult = "{\"imageCount\":0,\"videoCount\":1,\"confidenceScore\":0.0,\"conceptName\":\"Spring\",\"codeCount\":2,\"counterIndicator\":1,\"domainName\":\"Java\",\"metaUrl\":\"\",\"intent\":\"Illustration\",\"url\":\"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html\",\"titleUrl\":\"\"}{\"imageCount\":0,\"videoCount\":1,\"confidenceScore\":0.0,\"conceptName\":\"Spring\",\"codeCount\":2,\"counterIndicator\":1,\"domainName\":\"Java\",\"metaUrl\":\"\",\"intent\":\"Illustration\",\"url":\"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html\",\"titleUrl":\"\"}{\"imageCount":0,\"videoCount\":1,\"confidenceScore\":0.0,\"conceptName\":\"Spring\",\"codeCount\":2,\"counterIndicator\":1,\"domainName\":\"Java\",\"metaUrl\":\"\",\"intent\":\"Illustration\",\"url\":\"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html\",\"titleUrl":\"\"}{\"imageCount\":0,\"videoCount\":1,\"confidenceScore\":0.0,\"conceptName\":\"Spring\",\"codeCount\":2,\"counterIndicator\":1,\"domainName\":\"Java\",\"metaUrl\":\"\",\"intent\":\"Illustration",\"url\":\"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html\",\"titleUrl\":\"\"}";

		//String expectedResult = "{"imageCount":0,"videoCount":1,"confidenceScore":0.0,"conceptName":"Spring","codeCount":2,"counterIndicator":1,"domainName":"Java","metaUrl":"","intent":"Illustration","url":"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html","titleUrl":""}{"imageCount":0,"videoCount":1,"confidenceScore":0.0,"conceptName":"Spring","codeCount":2,"counterIndicator":1,"domainName":"Java","metaUrl":"","intent":"Illustration","url":"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html","titleUrl":""}{"imageCount":0,"videoCount":1,"confidenceScore":0.0,"conceptName":"Spring","codeCount":2,"counterIndicator":1,"domainName":"Java","metaUrl":"","intent":"Illustration","url":"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html","titleUrl":""}{"imageCount":0,"videoCount":1,"confidenceScore":0.0,"conceptName":"Spring","codeCount":2,"counterIndicator":1,"domainName":"Java","metaUrl":"","intent":"Illustration","url":"https:\/\/docs.spring.io\/spring\/docs\/3.0.x\/spring-framework-reference\/html\/overview.html","titleUrl":""}";
		//System.out.println(expectedResult.toString());
		System.out.println(actualResult);
		System.out.println(expectedResult);
		
		assertThat(actualResult.toString(), is(equalTo(expectedResult.toString())));
		
	}

}
