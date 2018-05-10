package com.stackroute.service;

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.redisson.IntentModel;

@Service
public class IntentPopulation {
	
	private Driver driver;

	@Value("${uri}")
	String uri;
	@Value("${username}")
	String user;
	@Value("${password}")
	String password;
	
	public void neo4jPopulation(Map<String,Map<IntentModel,ArrayList<String>>> synonymMap){	
		intentPopulation(synonymMap, "Illustration");
	}
	public void  intentPopulation(Map<String,Map<IntentModel,ArrayList<String>>> synonymMap,String intent1){
		Map<IntentModel,ArrayList<String>> tempList = synonymMap.get(intent1);
		for(Map.Entry<IntentModel, ArrayList<String>> entry : tempList.entrySet()){
			IntentModel intentModel = entry.getKey();
			String intent = intentModel.getIntent();
			String term = intentModel.getTerm();
			String weight =intentModel.getWeight();
			ArrayList<String> synoTermList = entry.getValue();
			if(!synoTermList.isEmpty()){
			for(int i =0;i<synoTermList.size();i++){
				String Query ="Match(i:Intent{name:\""+intent+"\"})-[:Is_An]->(r:Root) Merge(n:Word {name:\""+synoTermList.get(i)+"\",weight:\""+weight+"\"})-[:Is_A]->(i)";
				driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
				Session session = driver.session();
				String Message = session.writeTransaction(new TransactionWork<String>() {
					public String execute(Transaction tx) {
						StatementResult result = tx.run(Query);
						return "Term is populated with weight"+weight;
					}
				});
				System.out.println(synoTermList.get(i)+" is populated with "+weight);
			}
			
			}
			System.out.println("All Synonyms of "+term+" is populated");
					}
		
		
	}
	

}
