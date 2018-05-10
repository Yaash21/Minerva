package com.stackroute.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.domain.SearchResult;

@Repository
/**
 * Extending Mongo Repository and declaring three custom methods.
 * 
 * @author
 *
 */
public interface SearchRepository extends MongoRepository<SearchResult, Integer> {

	/**
	 * deleteByConcept method will be self implemented using Springboot Application
	 * and will delete SearchResult object using concept
	 * 
	 * @param concept
	 */
	void deleteByDomainAndConcept(String domain, String concept);

	
	/**
	 * findByDomainAndConcept will be self implemented using Springboot Application and 
	 * will return SearchResult object with given domain and concept
	 * @param domain
	 * @param concept
	 * @return
	 */
	SearchResult findByDomainAndConcept(String domain, String concept);

}
