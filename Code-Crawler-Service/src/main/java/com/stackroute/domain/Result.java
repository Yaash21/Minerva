package com.stackroute.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;



@Component

/**
 * Result class with the setters and getters of all field names.
 * 
 */

public class Result {
	
	/**
	 * Name of the Domain
	 */
	
	private String domain;
	
	/**
	 * Name of the Concept
	 */

	private String concept;
	
	/**
	 * Time when that concept is queried
	 */

	private Date date;
	
	/**
	 * List of the URL's
	 */

	private List<String> urls;
	
	/**
	 * getter for domain
	 * 
	 * @return
	 */

	public String getDomain() {
		return domain;
	}
	
	/**
	 * setter for domain
	 * 
	 * @param domain
	 */
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * getter for domain
	 * 
	 * @return
	 */
	public String getConcept() {
		return concept;
	}
	
	/**
	 * setter for concept
	 * 
	 * @param concept
	 */

	public void setConcept(String concept) {
		this.concept = concept;
	}
	
	/**
	 * getter for Date
	 * 
	 * @return
	 */

	public Date getDate() {
		return date;
	}
	
	/**
	 * setter for date
	 * 
	 * @param date
	 */

	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * getter for url's
	 * 
	 * @return
	 */

	public List<String> getUrls() {
		return urls;
	}
	
	/**
	 * setter for url's
	 * 
	 * @param urls
	 */

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	
	/**
	 * overriding toString function
	 */
	@Override
	public String toString() {
		return "Result [domain=" + domain + ", concept=" + concept + ", date=" + date + ", urls=" + urls + "]";
	}
	
	

}
