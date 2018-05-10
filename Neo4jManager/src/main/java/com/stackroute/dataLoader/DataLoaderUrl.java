package com.stackroute.dataLoader;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.stackroute.redisson.FetchUrl;
import com.stackroute.redisson.Neo4jUrlModel;

@Component
public class DataLoaderUrl implements ApplicationListener<ContextRefreshedEvent> {

	private Driver driver;

	@Value("${uri}")
	String uri;
	@Value("${username}")
	String user;
	@Value("${password}")
	String password;
	@Value("${redisHost}")
	String redisHost;

	@Autowired
	private Neo4jUrlModel neo4jUrlModel;

	private RBucket<Neo4jUrlModel> bucket;

	public RBucket<Neo4jUrlModel> getBucket() {


		return bucket;
	}

	public void setBucket(RBucket<Neo4jUrlModel> bucket) {
		this.bucket = bucket;
	}

	/**
	 * The class gets all the urls of concepts of each  Domain from Neo4j and puts to redis bucket "urlModel".
	 * The data structure used here is Map<String,Map<String,ArrayList<FetchUrl>>>.
	 * Fetchurl contains all the details of the Urls.
	 * ArrayList of fetchUrl objects is mapped with intent; mapped to concept;mapped to domain.
	 * Driver class is used to connect to Neo4j.
	 * Transaction class is used to run the query.
	 * @author yaash
	 *
	 */

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("ApplicationListener Invoked At Spring Container Startup for UrlList");
		String Query1="Match(n:url)-[x:Advance]->(c:concept)-[:SubConceptOf*]->(d:Domain)return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";
		String Query2= "Match(n:url)-[x:Illustration]->(c:concept)-[:SubConceptOf*]->(d:Domain)return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";	
		String Query3 = "Match(n:url)-[x:Intermediate]->(c:concept)-[:SubConceptOf*]->(d:Domain)return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";
		String Query4 = "Match(n:url)-[x:Beginner]->(c:concept)-[:SubConceptOf*]->(d:Domain)return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, c.name as conceptName,x.confidenceScore as confidenceScore, d.name as domainName";

		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);

		bucket = redisson.getBucket("urlModel");

		ArrayList<FetchUrl> fetchListAdvance = runQuery(Query1,"Advance");

		ArrayList<FetchUrl> fetchListIllustration = runQuery(Query2,"Illustration");

		ArrayList<FetchUrl> fetchListIntermediate = runQuery(Query3,"Intermediate");

		ArrayList<FetchUrl> fetchListBeginner = runQuery(Query4,"Beginner");


		Map<String,ArrayList<FetchUrl>> conceptMapAdvance = mapping(fetchListAdvance);

		Map<String,ArrayList<FetchUrl>> conceptMapIllustration = mapping(fetchListIllustration);

		Map<String,ArrayList<FetchUrl>> conceptMapIntermediate = mapping(fetchListIntermediate);

		Map<String,ArrayList<FetchUrl>> conceptMapBeginner = mapping(fetchListBeginner);


		Map<String,Map<String,ArrayList<FetchUrl>>> conceptfetchMap = new HashMap<>();
		for (Entry<String, ArrayList<FetchUrl>> entry : conceptMapBeginner.entrySet()){
			String concept= entry.getKey();
			Map<String,ArrayList<FetchUrl>>	tempMap = new HashMap<>();
			tempMap.put("Beginner",entry.getValue());
			tempMap.put("Intermediate", conceptMapIntermediate.get(concept));
			tempMap.put("Illustration",conceptMapIllustration.get(concept));
			tempMap.put("Advance", conceptMapAdvance.get(concept));
			conceptfetchMap.put(concept, tempMap);
		}
		neo4jUrlModel.setUrlMap(conceptfetchMap);
		bucket.set(neo4jUrlModel);


	}
	/**
	 * This method gets the list of the Urls linked to a specific concept and a particular Intent.
	 * @param Query
	 * @param intent
	 * @return
	 */
	public ArrayList<FetchUrl> runQuery(String Query, String intent){

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
	/**
	 * this method maps the Intent to all the Urls(of a specific concept+domain) of that intent .
	 * @param fetchList
	 * @return
	 */
	public Map<String,ArrayList<FetchUrl>> mapping (ArrayList<FetchUrl> fetchList){

		Map<String,ArrayList<FetchUrl>> conceptMap = new HashMap<>();
		for(int i=0; i<fetchList.size();i++){
			FetchUrl temp = fetchList.get(i);
			String concept = fetchList.get(i).getConceptName();
			String domain = fetchList.get(i).getDomainName();
			String key = concept+"+"+domain;
			if(!conceptMap.containsKey(key)){
				ArrayList<FetchUrl> fetchListTemp = new ArrayList<>();
				fetchListTemp.add(temp);
				conceptMap.put(key,fetchListTemp);

			}else{
				ArrayList<FetchUrl> tempList = conceptMap.get(key);
				tempList.add(temp);
				conceptMap.put(key,tempList);

			}

		}
		return conceptMap;

	}






}


