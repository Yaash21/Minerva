package com.stackroute.redisson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Neo4jUrlModel {
	
	private Map<String,Map<String,ArrayList<FetchUrl>>> urlMap;

	public Map<String, Map<String, ArrayList<FetchUrl>>> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, Map<String, ArrayList<FetchUrl>>> urlMap) {
		this.urlMap = urlMap;
	}

	

	
}
