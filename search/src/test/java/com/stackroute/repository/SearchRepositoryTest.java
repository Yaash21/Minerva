//package com.stackroute.repository;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.stackroute.configuration.RepositoryConfiguration;
//import com.stackroute.domain.SearchQuery;
//import com.stackroute.domain.SearchResult;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = { RepositoryConfiguration.class })
///**
// * SearchRepositoryTest is a jUnit test class which is testing for the
// * working of the SearchRepository methods
// * 
// * @author
// *
// */
//public class SearchRepositoryTest {
//
//	/**
//	 * creating an object of SearchRepository
//	 */
//	private SearchRepository searchRepository;
//	
//	
//	
//	/**
//	 * SearchResult object to use it in the test case validation
//	 */
//	private SearchResult searchResult;
//
//	/**
//	 * SearchQuery object to use it in the test case validation
//	 */
//	private SearchQuery searchQuery;
//	
//	
//	/**
//	 * 
//	 */
//	private List<SearchResult> searchResults;
//	
//	@Autowired
//	/**
//	 * autowired setter for the SearchRepository object
//	 * 
//	 * @param SearchRepository
//	 */
//	public void setSearchRepository(final SearchRepository searchRepository) {
//		this.searchRepository = searchRepository;
//	}
//
//	@Before
//	/**
//	 * setUp method will execute once before very test case execution
//	 */
//	public void setUp() {
//		searchResults = new ArrayList<SearchResult>(); 
//		searchQuery = new SearchQuery();
//		searchQuery.setDomain("Java");
//		List<String> concepts = new ArrayList<String>();
//		concepts.add("service");
//		searchQuery.setConcepts(concepts);
//		searchQuery.setDate(new Date());
//		
//		searchResult = new SearchResult();
//		searchResult.setDomain("Java");
//		searchResult.setConcept("service");
//		searchResult.setDate(searchQuery.getDate());
//		List<String> urls = new ArrayList<String>();
//		urls.add("https://forge.ow2.org/projects/javaservice");
//		urls.add("https://wrapper.tanukisoftware.com/doc/english/introduction.html");
//        urls.add("https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Service.html");
//        urls.add("https://wrapper.tanukisoftware.com/");
//        urls.add("https://cloud.oracle.com/en_US/java");
//        urls.add("http://javaservice.ow2.org/index.html");
//        urls.add("https://en.wikipedia.org/wiki/Service_wrapper");
//        urls.add("https://www.wavemaker.com/learn/app-development/services/java-services/java-service/");
//        urls.add("http://yajsw.sourceforge.net/");
//        urls.add("https://stackoverflow.com/questions/68113/how-to-create-a-windows-service-from-java-app");
//		searchResult.setUrls(urls);
//		searchResults.add(searchResult);
//
//	}
//
//	@After
//	/**
//	 * tearDown will be executed after every test case execution and will clean
//	 * the repository
//	 */
//	public void tearDown() {
//		searchResults.clear();
//	}
//
//	@Test
//	/**
//	 * testing method for findByDomainAndConcept method
//	 */
//	public final void shouldReturnSearchResultUsingFindByDomainAndConceptIsCalled() {
//		// Arrange
//		
//		SearchResult search = null;
//
//		for (final SearchResult searches : searchResults) {
//			if (searches.getConcept().equals("service") == true) {
//				search = searches;
//				break;
//			}
//		}
//		// Assert
//		assertEquals(search.getUrls(), searchRepository.findByDomainAndConcept("Java", "service").getUrls());
//
//	}
//
//
//}