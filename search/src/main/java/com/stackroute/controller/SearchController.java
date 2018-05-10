package com.stackroute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.domain.SearchQuery;
import com.stackroute.domain.SearchResult;
import com.stackroute.exception.TokenInvalidException;
import com.stackroute.services.SearchResultService;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

/**
 * controller class with five methods-
 * 
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
/**
 * 
 * SearchController class should call the takeQuery method of the SearchResultService
 * interface and return the objects to the client
 * 
 * @author
 *
 */
public class SearchController {

	/**
	 * An object of SearchResultService interface declared to be used as a
	 * reference for its implemented class
	 */
	SearchResultService searchResultService;

	@Autowired
	/**
	 * setter for the SearchResultService type object (either
	 * SearchResultServiceImpl 
	 * 
	 * @param SearchResultService
	 */
	public void setSearchResultService(final SearchResultService searchResultService) {
		this.searchResultService = searchResultService;
	}

	@PostMapping(value = "/search", produces = "application/json")
	/**
	 * This takeQuery method is calling another takeQuery method from
	 * SearchResultService to add the searchResult object in the database
	 * 
	 * @param SearchQuery
	 * @return SearchResult
	 */
	public ResponseEntity<?> takeQuery(@RequestHeader("token") String token,@RequestBody final SearchQuery searchQuery) {
	
		String isValid = searchResultService.validateToken(token);
		System.out.println(token);
		boolean flag=false;
		
		System.out.println(isValid);
		List<SearchResult> processedResult = null;
		
		//isValid="Failed";
		
		System.out.println(isValid);
		if(isValid.equals("Failed"))
		{
			String message="Token is invalid";
			return new ResponseEntity<String>(message, HttpStatus.CREATED);
		}
		
		else
		{
			processedResult = searchResultService.takeQuery(searchQuery);
			System.out.println(processedResult);
			return new ResponseEntity<List<SearchResult>>(processedResult, HttpStatus.CREATED);
		}

	}

}
