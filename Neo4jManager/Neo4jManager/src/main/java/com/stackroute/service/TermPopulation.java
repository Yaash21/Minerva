package com.stackroute.service;

import java.util.ArrayList;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.TermAngularModel;

@Service
public class TermPopulation {
	
	private Driver driver;

	@Value("${uri}")
	String uri;
	@Value("${username}")
	String user;
	@Value("${password}")
	String password;
	
	public String synonymPopulation(TermAngularModel termAngularModel){
		
		ArrayList<IntentModel> synoList =  termAngularModel.getNeo4jList();
		for(int i=0;i<synoList.size();i++){
			IntentModel intentModel = synoList.get(i);
			String term = intentModel.getTerm();
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
		
		System.out.println("All Synonyms of are populated");
		return "Working Fine";
				
			
		
	}

}
