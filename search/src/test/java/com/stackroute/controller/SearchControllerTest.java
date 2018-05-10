package com.stackroute.controller;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.domain.SearchQuery;
import com.stackroute.domain.SearchResult;
import com.stackroute.services.GoogleApiSearchService;
import com.stackroute.services.SearchResultService;

//Test for Controller.
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
/**
 * RestaurantControllerTest is the jUnit Test class for testing the working of
 * RestaurantController class
 * 
 * @author
 *
 */
public class SearchControllerTest {

	/**
	 * restaurant object to use it in the test case validation
	 */
	private SearchResult searchResult;

	/**
	 * restaurant object to use it in the test case validation
	 */
	private SearchQuery searchQuery;
	
	
	/**
	 * 
	 */
	private List<SearchResult> searchResults;
	

	@MockBean
	/**
	 * Creating a MockBean of RestaurantService interface
	 */
	private SearchResultService searchService;
	
	@MockBean
	/**
	 * Mocking GoogleApiSearchService 
	 */
	private GoogleApiSearchService googleSearchService;


	@Before
	/**
	 * setUp() will execute before every test case and will create two
	 * restaurant objects
	 */
	public void setUp() {
		
		searchResults = new ArrayList<SearchResult>(); 
		searchQuery = new SearchQuery();
		searchQuery.setDomain("Java");
		List<String> concepts = new ArrayList<String>();
		concepts.add("service");
		searchQuery.setConcepts(concepts);
		searchQuery.setDate(new Date());
		
		searchResult = new SearchResult();
		searchResult.setDomain("Java");
		searchResult.setConcept("service");
		searchResult.setDate(searchQuery.getDate());
		List<String> urls = new ArrayList<String>();
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
	}


	@Test
	/**
	 * this method will test takeQuery Method
	 * 
	 * @throws Exception
	 */
	public void testForTakingQuery() throws Exception {
		// Arrange
		given(searchService.takeQuery(searchQuery)).willReturn(searchResults);
		// Act
	}
	

	
	
	

}
