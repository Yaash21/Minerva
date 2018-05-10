package com.stackroute.service;

import java.util.ArrayList;

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
import org.springframework.stereotype.Service;

import com.stackroute.dataLoader.DataLoaderIntent;
import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.Neo4jIntentModel;
import com.stackroute.redisson.TermAngularModel;


/**
 * The method of this class is invoked when Domain Expert populates a term in the Intent Graph.
 * The method is invoked via rest end point.
 * Driver class is used to connect to Neo4j.
 * Transaction class is used to run the query. 
 * @author yaash
 *
 */
@Service
public class TermPopulation {
	
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
	private Neo4jIntentModel neo4jIntentModel;
	private RBucket<Neo4jIntentModel> bucket;
	
	/**
	 * This method is to populatre the new term in intent graph.
	 * It uses a Merge Query to avoid duplicates.
	 * @param termAngularModel
	 * @return
	 */
	public String synonymPopulation(TermAngularModel termAngularModel){
		
		ArrayList<IntentModel> synoList =  termAngularModel.getNeo4jList();
		for(int i=0;i<synoList.size();i++){
			IntentModel intentModel = synoList.get(i);
			String term = intentModel.getTerm().toLowerCase();
			String intent = intentModel.getIntent();
			String weight = intentModel.getWeight();
			String Query ="Match(i:Intent{name:\""+intent+"\"})-[:Is_An]->(r:Root) Merge(n:Word {name:\""+term+"\",weight:\""+weight+"\"})-[:Is_A]->(i)";
			driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
			Session session = driver.session();
			String Message = session.writeTransaction(new TransactionWork<String>() {
				public String execute(Transaction tx) {
					StatementResult result = tx.run(Query);
					return term+" is populated with weight "+weight;
				}
			});
			System.out.println(term +" is populated with "+weight);
		}
		
		refreshIntent(synoList);
		System.out.println("All Synonyms of are populated");
		
		return "Working Fine";
				
		
	}
/**
 * This method gets all the new terms from intent graph.
 * This method fully refreshes the redis data to give the Domain expert the list of all terms at any moment.
 * @param synoList
 */
	public void refreshIntent(ArrayList<IntentModel> synoList){
	System.out.println("ApplicationListener Invoked At Spring Container Startup for ConceptList");
	Config config = new Config();
	config.useSingleServer().setAddress(redisHost);
	RedissonClient redisson = Redisson.create(config);
	
	bucket = redisson.getBucket("intentModel");
	ArrayList<IntentModel> intentList = bucket.get().getIntentList();
	for(int i=0;i<synoList.size();i++){
		IntentModel intentModel = synoList.get(i);
		String term = intentModel.getTerm();
		String intent = intentModel.getIntent();
		String weight = intentModel.getWeight();
		String Query= "Match(w:Word{name:\""+term+"\"})-[Is_A]->(i:Intent{name:\""+intent+"\"}) return w.name as term, i.name as intent, w.weight as weight";
		
	driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	Session session1 = driver.session();
	String Message1 = session1.writeTransaction(new TransactionWork<String>() {
		public String execute(Transaction tx) {
			StatementResult result = tx.run(Query);
			while (result.hasNext()) {

				Record record = result.next();
				IntentModel intentModel = new IntentModel();
				intentModel.setTerm(record.get("term").asString());
				intentModel.setIntent(record.get("intent").asString());
				try{
				intentModel.setWeight(record.get("weight").asString());
				}catch(Exception e){
					intentModel.setWeight(record.get("weight").toString());
				}
				intentList.add(intentModel);
			}
			
			return "Neo4j ConceptList Working";
		}
	});
	}
	neo4jIntentModel.setIntentList(intentList);
	bucket.set(neo4jIntentModel);
	}

}
