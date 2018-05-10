package com.stackroute.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document
/**
 * SearchResult class with the setters and getters of all field names.
 * 
 */
public class SearchResult {

	/**
	 * Domain from our concept graph
	 */
	private String domain;

	/**
	 * Name of the concept
	 */
	private String concept;

	/**
	 *  Entry date for search result
	 */
	private Date date;

	/**
	 * List of urls after using google API
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
	 * getter for the concept
	 * 
	 * @return
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * setter for Concept
	 * 
	 * @param concept
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * setter for entry date
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * getter for the entry date
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * getter for the urls
	 * 
	 * @return
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * setter for urls
	 * 
	 * @param List<String>
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}



	@Override
	/**
	 * returning a complete list of entities value.
	 */
	public String toString() {
		return "SearchResult [domain=" + domain + ", concept=" + concept + ", date=" + date + ", urls=" + urls + "]";
	}

	@Override
	/**
	 * overriding the object hashCode for comparing multiple Search objects
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concept == null) ? 0 : concept.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((urls == null) ? 0 : urls.hashCode());
		return result;
	}

	@Override
	/**
	 * Overriding the Object class equalTo method in order to compare objects on
	 * the basis of fields
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		if (concept == null) {
			if (other.concept != null)
				return false;
		} else if (!concept.equals(other.concept))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (urls == null) {
			if (other.urls != null)
				return false;
		} else if (!urls.equals(other.urls))
			return false;
		return true;
	}


}
