package com.stackroute.controller;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.SearchApplication;
import com.stackroute.domain.SearchQuery;
import com.stackroute.domain.SearchResult;

//Integration testing
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * SearchControllerIT is the integration test class
 * 
 * @author
 *
 */
public class SearchControllerIT {

	@LocalServerPort
	/**
	 * creating a port entity just to handle the port values
	 */
	private int port;

	/**
	 * creating a TestRestTemplate to work on the integration test
	 */
	private TestRestTemplate restTemplate = new TestRestTemplate();

	/**
	 * creating HttpHeaders object
	 */
	private HttpHeaders headers1 = new HttpHeaders();

	@Test
	/**
	 * method for testing the functionality of TakeQuery method of
	 * SearchController class
	 */
	public void testForTakeQuery() {

		final SearchResult searchResult = new SearchResult();
		final List<SearchResult> searchResults = new ArrayList<SearchResult>(); 
		final SearchQuery searchQuery = new SearchQuery();
		final List<String> concepts = new ArrayList<String>();
		final List<String> urls = new ArrayList<String>();

		searchQuery.setDomain("Java");
		concepts.add("service");
		searchQuery.setConcepts(concepts);
		searchQuery.setDate(new Date());

		searchResult.setDomain("Java");
		searchResult.setConcept("service");
		searchResult.setDate(searchQuery.getDate());
		urls.add("https://forge.ow2.org/projects/javaservice");
		urls.add("https://wrapper.tanukisoftware.com/doc/english/introduction.html");
		urls.add("https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Service.html");
		urls.add("https://wrapper.tanukisoftware.com/");
		urls.add("https://cloud.oracle.com/en_US/java");
		urls.add("http://javaservice.ow2.org/index.html");
		urls.add("https://en.wikipedia.org/wiki/Service_wrapper");
		urls.add("https://www.wavemaker.com/learn/app-development/services/java-services/java-service/");
		urls.add("http://yajsw.sourceforge.net/");
		urls.add("https://stackoverflow.com/questions/68113/how-to-create-a-windows-service-from-java-app");
		searchResult.setUrls(urls);
		searchResults.add(searchResult);
		
		HttpEntity<SearchQuery> entity1 = new HttpEntity<SearchQuery>(searchQuery, headers1);
		ParameterizedTypeReference<List<SearchResult>> typeRef = new ParameterizedTypeReference<List<SearchResult>>() {};
		ResponseEntity<List<SearchResult>> response1 =  (ResponseEntity<List<SearchResult>>) restTemplate.exchange(createURLWithPort("/api/v1/search"),
				HttpMethod.POST, entity1, typeRef);

		response1.getBody().get(0).setDate(searchQuery.getDate());

		assertEquals(searchResults.toString(), response1.getBody().toString());
		
	}


	/**
	 * Method generates the url where the application is running.
	 * 
	 * @param uri
	 * @return String
	 */
	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}
}