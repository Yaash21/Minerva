package com.stackroute.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.redisson.FetchUrl;
import com.stackroute.redisson.Neo4jUrlModel;

@Service
public class FetchNeoUrl {
	
	@Autowired
	Neo4jUrlModel neo4jUrlModel;
	
	@Value("${redisHost}")
	String redisHost;
	
	public ArrayList<FetchUrl> fetchedUrl(String domain,String concept,String intent,boolean illustration){
		
	Config config = new Config();
	config.useSingleServer().setAddress(redisHost);
	RedissonClient redisson = Redisson.create(config);
	RBucket<Neo4jUrlModel> bucket = redisson.getBucket("urlModel");
	ArrayList<FetchUrl> fetchedList=new ArrayList<>();
	neo4jUrlModel.setUrlMap(bucket.get().getUrlMap());
	String key = concept+"+"+domain ;
	Map<String,Map<String,ArrayList<FetchUrl>>> myConceptMap = neo4jUrlModel.getUrlMap();
	if(myConceptMap.containsKey(key)){
	Map<String,ArrayList<FetchUrl>> myIntentMap= myConceptMap.get(key);
	 fetchedList = myIntentMap.get(intent);
	 for(FetchUrl fetchUrl:fetchedList){
		 System.out.println(fetchUrl.getUrl());
	 }
	 if(illustration==true){
	 for(int i =0;i<fetchedList.size();i++){
		 FetchUrl fetchUrl = fetchedList.get(i);
		double imgCount = (double)fetchUrl.getImgCount();
		double videoCount = (double)fetchUrl.getVideoCount();
		double codeCount = (double)fetchUrl.getCodeCount();
		double confidence = fetchUrl.getConfidenceScore();
		 
		double totalScore = imgCount+videoCount+codeCount+confidence;
		 fetchUrl.setConfidenceScore(totalScore);
	 }
	 }
	 
	fetchedList.sort(Comparator.comparing(FetchUrl::getConfidenceScore, (s1, s2) -> {
		return s2.compareTo(s1);
	}));
	}
	return fetchedList;
	}
	}

