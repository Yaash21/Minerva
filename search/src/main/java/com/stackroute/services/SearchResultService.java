package com.stackroute.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stackroute.domain.SearchQuery;
import com.stackroute.domain.SearchResult;

@Service
/**
 * 
 * SearchResultService interface will contain following methods declaration
 * takeQuery, addSearchResult, deleteSearchResult and searchByConcept
 * 
 * @author
 *
 */
public interface SearchResultService {

	
	/**
	 * 
	 * @param searchQuery
	 * @return
	 */
	List<SearchResult> takeQuery(SearchQuery searchQuery);
	
	
	/**
	 * 
	 * @param searchResult
	 * @return SearchResult
	 */
	SearchResult addSearchResult(SearchResult searchResult);

	/**
	 * 
	 * @param rconcept
	 * @return String
	 */
	String deleteSearchResult(String domain, String concept);

	/**
	 * 
	 * @param domain
	 * @param concept
	 * @return
	 */
	SearchResult searchByDomainAndConcept(String domain, String concept);


	String validateToken(String token);
	
}
