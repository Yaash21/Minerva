package com.stackroute.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.Neo4jIntentModel;
import com.stackroute.redisson.TermBankSynonyms;


@Service
public class SynonymPopulation  implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	WordApiService wordApiService ;

	@Autowired
	TermBankSynonyms termBank;
	
	@Autowired
	IntentPopulation intentPopulation;
	
	private RBucket<TermBankSynonyms> bucket1;
	

	public RBucket<TermBankSynonyms> getBucket1() {
		return bucket1;
	}


	public void setBucket1(RBucket<TermBankSynonyms> bucket1) {
		this.bucket1 = bucket1;
	}
	
	@Value("${redisHost}")
	String redisHost;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);
		bucket1 = redisson.getBucket("TermModel");
		RBucket<Neo4jIntentModel> bucket = redisson.getBucket("intentModel");
		ArrayList<IntentModel> termList = bucket.get().getIntentList();
	
		Map<String,Map<IntentModel,ArrayList<String>>> synonymMap = new HashMap<>();
		 Map<IntentModel,ArrayList<String>> termMap = new HashMap<>();
		for(int i=0;i<termList.size();i++){
			IntentModel intentModel = termList.get(i);
			String intent=intentModel.getIntent();
			System.out.println(intent);
			String term = intentModel.getTerm();
			if(intent.equals("Illustration")){
			 ArrayList<String> synonyms = wordApiService.ApiResults(term);
			 System.out.println(synonyms.toString());
			termMap.put(intentModel, synonyms);
//			if(synonymMap.containsKey(intent)){
				synonymMap.put(intent, termMap);
//			}else{
//				synonymMap.put(intent, termMap);
//			}
			}
		}
		termBank.setSynoMap(synonymMap);
		bucket1.set(termBank);
		intentPopulation.neo4jPopulation(synonymMap);

	}

}
