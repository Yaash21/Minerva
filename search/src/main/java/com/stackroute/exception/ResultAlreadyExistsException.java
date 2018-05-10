package com.stackroute.exception;

import com.stackroute.domain.SearchResult;
//import com.sun.mail.handlers.message_rfc822;

public class ResultAlreadyExistsException extends RuntimeException {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResultAlreadyExistsException(SearchResult res){
		super(res.getConcept() +" is already present");
		
		
		
	}

}

