package com.stackroute.service;

import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.domain.NeoFetch;
import com.stackroute.repository.RepositoryCS;



@Service
public class ConfidenceScoreService {

	@Autowired
	private RepositoryCS repositoryCS;
	
//	@Transactional(readOnly = true)
//	public List<NeoFetch> graph() {
//		List<NeoFetch> terms = repositoryCS.graph();
//		//Map<String,String> map = new LinkedHashMap<String,String>();
//		System.out.println("111");
//			System.out.println(terms.toString());
//			System.out.println("222");
//
//		//System.out.println(map);
//		return terms;
//	}
	
	@Transactional(readOnly = true)
	public List<NeoFetch> graph(){
		List<NeoFetch> wordlist = repositoryCS.graph();
		return wordlist;
	}
	
}
		
		

	
