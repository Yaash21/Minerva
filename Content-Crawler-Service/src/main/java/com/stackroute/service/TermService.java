package com.stackroute.service;

import java.io.File;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.repository.TermRepository;



@Service
public class TermService {

	private List<String> terms;

	@Autowired
	private TermRepository termRepository;
	
	@Transactional(readOnly = true)
	public void graph() {
		terms = termRepository.graph();
		System.out.println(terms);
	}
	
	public List<String> getTerms() {
		return terms;
	}
}