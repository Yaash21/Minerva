package com.stackroute.redisson;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TermBankSynonyms {
	
	
	private Map<String,Map<IntentModel,ArrayList<String>>> synoMap;

	public Map<String,Map<IntentModel, ArrayList<String>>> getSynoMap() {
		return synoMap;
	}

	public void setSynoMap(Map<String,Map<IntentModel, ArrayList<String>>> synoMap) {
		this.synoMap = synoMap;
	}
	

}
