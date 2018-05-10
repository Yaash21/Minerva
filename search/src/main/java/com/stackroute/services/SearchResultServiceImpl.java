package com.stackroute.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stackroute.domain.SearchQuery;
import com.stackroute.domain.SearchResult;
import com.stackroute.exception.ResultAlreadyExistsException;
import com.stackroute.repository.SearchRepository;

import io.jsonwebtoken.Jwts;

@Service
/**
 * RestaurantServiceImpl class is implementing RestaurantService interface and
 * its methods are defined here.
 * 
 * @author
 *
 */
public class SearchResultServiceImpl implements SearchResultService {

	  static final long EXPIRATIONTIME = 864000; // 10 days
	  static final String SECRET = "ThisIsASecret";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";
	@Value("${exchange}")
	private String exchange;
	
	@Value("${queueKey}")
	private String queueKey;

	/**
	 * Defining an object of RestaurantReposistory so that the methods from
	 * MongoRepository can be used
	 */
	private SearchRepository searchRepository;
	
	/**
	 * searchResult will store everything to be stored and to be published
	 */
	private SearchResult searchResult;
	
	/**
	 * an instance of googleapi service
	 */
	private GoogleApiSearchService googleApiSearchService;
	
	/**
	 * An amqpTemplate
	 */
	private AmqpTemplate amqpTemplate;
	
	
	@Autowired
	/**
	 * 
	 * @param amqpTemplate
	 */
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	@Autowired
	/**
	 * 
	 * @param googleApiSearchService
	 */
	public void setGoogleApiSearchService(GoogleApiSearchService googleApiSearchService) {
		this.googleApiSearchService = googleApiSearchService;
	}
		
	@Autowired
	/**
	 * 
	 * @param searchResult
	 */
	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	@Autowired
	/**
	 * setter for the searchRepository object created above. It has been
	 * autowired for automatically bean declaration
	 * 
	 * @param searchRepository
	 */
	public void setsearchRepository(final SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	/**
	 * SearchResult object is added by this method using save method from the
	 * searchResultRepository
	 * 
	 * @return searchResult
	 */
	public SearchResult addSearchResult(final SearchResult searchResult) {
		try{
			searchRepository.save(searchResult);
			}catch(DuplicateKeyException de){
				throw new ResultAlreadyExistsException(searchResult);
			}
		return searchResult;
		
	}

	/**
	 * SearchResult object is deleted by this method using deleteByConcept method from
	 * the searchResultRepository
	 * 
	 * @return String
	 */
	public String deleteSearchResult(final String domain, final String concept) {
		searchRepository.deleteByDomainAndConcept(domain, concept);
		return "searchResult deleted";
	}

	/**
	 * SearchResult object is searched by this method using findByConcept method from
	 * the searchResultRepository
	 * 
	 * @return searchResult
	 */
	public SearchResult searchByDomainAndConcept(final String domain, final String concept) {
		return searchRepository.findByDomainAndConcept(domain, concept);
	}

	@Value("${timecheck}")
	float checkForHour;
	
	/**
	 * just taking a searchQuery and returning it back ...rest service will be writing later
	 * 
	 */
	public List<SearchResult> takeQuery(SearchQuery searchQuery) {
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		searchQuery.setDate(new Date());
		for(int i=0; i<searchQuery.getConcepts().size(); ++i) {
			searchResult = new SearchResult();
			if(searchByDomainAndConcept(searchQuery.getDomain(),searchQuery.getConcepts().get(i))!= null ) {
				searchResult = searchByDomainAndConcept(searchQuery.getDomain(), searchQuery.getConcepts().get(i));
				float entryDifference = Math.abs(searchResult.getDate().getTime() - searchQuery.getDate().getTime());
				if((entryDifference / (60 * 60 * 1000))>checkForHour) {
					deleteSearchResult(searchQuery.getDomain(), searchQuery.getConcepts().get(i));
					searchResult.setDomain(searchQuery.getDomain());
					searchResult.setConcept(searchQuery.getConcepts().get(i));
					searchResult.setDate(searchQuery.getDate());
					searchResult.setUrls(googleApiSearchService.getUrls(searchQuery.getDomain(), searchQuery.getConcepts().get(i)));
					addSearchResult(searchResult);
				}
				else
					addSearchResult(searchResult);
			}
			else {
				searchResult.setDomain(searchQuery.getDomain());
				searchResult.setConcept(searchQuery.getConcepts().get(i));
				searchResult.setDate(searchQuery.getDate());
				searchResult.setUrls(googleApiSearchService.getUrls(searchQuery.getDomain(), searchQuery.getConcepts().get(i)));
				addSearchResult(searchResult);
			}
			if(searchResult.getDate() == searchQuery.getDate() ) {
				Gson gson = new Gson();
                Type type = new TypeToken<SearchResult>() {}.getType();
                String json = gson.toJson(searchResult, type);
				amqpTemplate.convertAndSend(exchange,queueKey, json);
				searchResults.add(searchResult);
			}
		}
		return searchResults;
	}

	@Override
	public String validateToken(String token) {
		// TODO Auto-generated method stub
		
		String result="";
	    if (token != null) {
	        // parse the token.
	        Date expiryDate = Jwts.parser()
	            .setSigningKey(SECRET)
	            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
	            .getBody()
	            .getExpiration();
	        
	        //System.out.println(date);
	        
	        //System.out.println(new Date(System.currentTimeMillis()));
	        
	        long currentTimeMs=System.currentTimeMillis();
	        System.out.println(expiryDate);
	        Date date=new Date(currentTimeMs);
	        
	        
	        if(date.compareTo(expiryDate)>=0)
	        {
	      	   result="Failed";
	        }
	        else{
	      	  result="Successful";
	        }

	      }
		return result;
	}

}