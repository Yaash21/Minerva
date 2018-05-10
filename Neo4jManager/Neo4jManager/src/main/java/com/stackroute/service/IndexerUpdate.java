package com.stackroute.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.redisson.FetchUrl;
import com.stackroute.redisson.Neo4jUrlModel;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class IndexerUpdate {
	private Driver driver;

	@Value("${uri}")
	String uri;
	@Value("${username}")
	String user;
	@Value("${password}")
	String password;
	@Value("${redisHost}")
	String redisHost;

	public String refresh(String concept, String domain) {

		String Query1 = "Match(n:url)-[x:Advance]->(c:concept{name:\"" + concept + "\"})-[:SubConceptOf*]->(d:Domain{name:\"" + domain
				+ "\"})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";
		String Query2 = "Match(n:url)-[x:Illustration]->(c:concept{name:\"" + concept + "\"})-[:SubConceptOf*]->(d:Domain{name:\"" + domain
				+ "\"})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";
		String Query3 = "Match(n:url)-[x:Intermediate]->(c:concept{name:\"" + concept + "\"})-[:SubConceptOf*]->(d:Domain{name:\"" + domain
				+ "\"})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";
		String Query4 = "Match(n:url)-[x:Beginner]->(c:concept{name:\"" + concept + "\"})-[:SubConceptOf*]->(d:Domain{name:\"" + domain
				+ "\"})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";

		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);
		RBucket<Neo4jUrlModel> bucket = redisson.getBucket("urlModel");

		ArrayList<FetchUrl> fetchListAdvance = runQuery(Query1, "Advance");
System.out.println("me");
		ArrayList<FetchUrl> fetchListIllustration = runQuery(Query2, "Illustration");

		ArrayList<FetchUrl> fetchListIntermediate = runQuery(Query3, "Intermediate");

		ArrayList<FetchUrl> fetchListBeginner = runQuery(Query4, "Beginner");
		if(fetchListAdvance.isEmpty()){
			System.out.println("Working not fine");
			
			return "Working not fine";
			
		}

		Map<String, ArrayList<FetchUrl>> conceptMapAdvance = mapping(fetchListAdvance);

		Map<String, ArrayList<FetchUrl>> conceptMapIllustration = mapping(fetchListIllustration);

		Map<String, ArrayList<FetchUrl>> conceptMapIntermediate = mapping(fetchListIntermediate);

		Map<String, ArrayList<FetchUrl>> conceptMapBeginner = mapping(fetchListBeginner);

		Map<String, Map<String, ArrayList<FetchUrl>>> conceptfetchMap = bucket.get().getUrlMap();
		for (Entry<String, ArrayList<FetchUrl>> entry : conceptMapBeginner.entrySet()) {
			String concept1 = entry.getKey();
			Map<String, ArrayList<FetchUrl>> tempMap = new HashMap<>();
			tempMap.put("Beginner", entry.getValue());
			tempMap.put("Intermediate", conceptMapIntermediate.get(concept));
			tempMap.put("Illustration", conceptMapIllustration.get(concept));
			tempMap.put("Advance", conceptMapAdvance.get(concept));
			conceptfetchMap.put(concept1, tempMap);
		}
		Neo4jUrlModel neo4jUrlModel = new Neo4jUrlModel();
		neo4jUrlModel.setUrlMap(conceptfetchMap);
		bucket.set(neo4jUrlModel);
		String working = "You just populated one concept";
		return working;

	}

	public ArrayList<FetchUrl> runQuery(String Query, String intent) {

		ArrayList<FetchUrl> fetchList = new ArrayList<>();
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		Session session1 = driver.session();
		String Message1 = session1.writeTransaction(new TransactionWork<String>() {
			public String execute(Transaction tx) {
				StatementResult result = tx.run(Query);
				while (result.hasNext()) {

					Record record = result.next();
					FetchUrl fetchUrl = new FetchUrl();
					fetchUrl.setCodeCount(record.get("codeCount").asInt());
					fetchUrl.setImgCount(record.get("imgCount").asInt());
					fetchUrl.setVideoCount(record.get("videoCount").asInt());
					fetchUrl.setCounterIndicator(record.get("counterIndicator").asInt());
					fetchUrl.setConfidenceScore(record.get("confidenceScore").asDouble());
					fetchUrl.setConceptName(record.get("conceptName").asString());
					fetchUrl.setDomainName(record.get("domainName").asString());
					fetchUrl.setMetaUrl(record.get("metaUrl").asString());
					fetchUrl.setTitleUrl(record.get("titleUrl").asString());
					fetchUrl.setUrl(record.get("url").asString());
					fetchUrl.setIntentUrl(intent);
					fetchList.add(fetchUrl);

				}
				return "Neo4j ConceptList Working";
			}
		});
		return fetchList;

	}

	public Map<String, ArrayList<FetchUrl>> mapping(ArrayList<FetchUrl> fetchList) {

		Map<String, ArrayList<FetchUrl>> conceptMap = new HashMap<>();
		for (int i = 0; i < fetchList.size(); i++) {
			FetchUrl temp = fetchList.get(i);
			String concept = fetchList.get(i).getConceptName();
			String domain = fetchList.get(i).getDomainName();
			String key = concept + "+" + domain;
			if (!conceptMap.containsKey(key)) {
				ArrayList<FetchUrl> fetchListTemp = new ArrayList<>();
				fetchListTemp.add(temp);
				conceptMap.put(key, fetchListTemp);

			} else {
				ArrayList<FetchUrl> tempList = conceptMap.get(key);
				tempList.add(temp);
				conceptMap.put(key, tempList);

			}

		}
		return conceptMap;

	}

}
