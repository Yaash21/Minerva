package com.stackroute.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
/**
 * SearchQuery class with the setters and getters of all field names.
 * 
 */
public class SearchQuery {

	/**
	 * Domain from our concept graph
	 */
	private String domain;

	/**
	 * List of concepts
	 */
	private List<String> concepts;

	/**
	 *  Entry date for search result
	 */
	private Date date;

	
	/**
	 * getter for the domain
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
	 * getter for the list of concepts
	 * 
	 * @return
	 */
	public List<String> getConcepts() {
		return concepts;
	}

	/**
	 * setter for List of Concepts
	 * 
	 * @param concepts
	 */
	public void setConcepts(List<String> concepts) {
		this.concepts = concepts;
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

	


	@Override
	/**
	 * returning a complete list of entities value.
	 */
	public String toString() {
		return "SearchQuery [domain=" + domain + ", concepts=" + concepts + ", date=" + date + "]";
	}

	@Override
	/**
	 * overriding the object hashCode for comparing multiple Search objects
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concepts == null) ? 0 : concepts.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
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
		SearchQuery other = (SearchQuery) obj;
		if (concepts == null) {
			if (other.concepts != null)
				return false;
		} else if (!concepts.equals(other.concepts))
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
		return true;
	}	


}
