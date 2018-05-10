package com.stackroute.services;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.stackroute.domain.SearchResult;
import com.stackroute.repository.SearchRepository;


@RunWith(MockitoJUnitRunner.class)
/**
 * 
 * Unit test for SearchResultServiceImpl class
 *
 */
public class SearchServiceImplTests {

	/**
	 * Creating an object SearchResultServiceImpl
	 */
	private SearchResultServiceImpl searchResultServiceImpl;

	@Mock
	/**
	 * Creating an object SearchRepository
	 */
	private SearchRepository searchRepository;

	@Mock
	/**
	 * Creating an object of SearchResult
	 */
	private SearchResult searchResult;

	@Mock
	/**
	 * Creating a List of SearchResult
	 */
	private List<SearchResult> searchResults;

	@Before
	/**
	 * setupMock() will be executed every time before each test
	 */
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
		searchResultServiceImpl = new SearchResultServiceImpl();
		searchResultServiceImpl.setsearchRepository(searchRepository);
	}

	@Test
	/**
	 * this method will test for the proper execution of findByDomainAndConcept method
	 * 
	 * @throws Exception
	 */
	public void shouldReturnSearchResultWhenFindByDomainAndConceptIsCalled() throws Exception {
		// Arrange
		when(searchRepository.findByDomainAndConcept("Java","service")).thenReturn(searchResult);
		// Act
		final SearchResult retrievedResult = searchResultServiceImpl.searchByDomainAndConcept("Java","service");
		// Assert
		assertThat(retrievedResult, is(equalTo(searchResult)));
	}

	
	
	@Test
	/**
	 * this method will test for the proper execution of addSearchResult
	 * 
	 * @throws Exception
	 */
	public void shouldReturnSearchResultWhenAddSearchResultIsCalled() throws Exception {
		// Arrange
		when(searchResultServiceImpl.addSearchResult(searchResult)).thenReturn(searchResult);
		// Act
		final SearchResult savedResult = searchResultServiceImpl.addSearchResult(searchResult);
		// Assert
		assertThat(savedResult, is(equalTo(searchResult)));
	}

	@Test
	/**
	 * this method will test for the proper execution of deleteByDomainAndConcept
	 * 
	 * @throws Exception
	 */
	public void shouldCallDeleteMethodOfSearchRepositoryWhenDeleteByDomainAndConceptIsCalled() throws Exception {
		// Arrange
		doNothing().when(searchRepository).deleteByDomainAndConcept("Java", "service");
		// RestaurantRepository my = Mockito.mock(RestaurantRepository.class);
		// Act
		searchResultServiceImpl.deleteSearchResult("Java", "service");
		// Assert
		verify(searchRepository, times(1)).deleteByDomainAndConcept("Java", "service");
	}

}
