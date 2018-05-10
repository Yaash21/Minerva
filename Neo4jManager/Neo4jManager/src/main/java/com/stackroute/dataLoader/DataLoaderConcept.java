package com.stackroute.dataLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.stackroute.redisson.Neo4jConceptModel;

@Component
public class DataLoaderConcept implements ApplicationListener<ContextRefreshedEvent> {

		private Driver driver;

		@Value("${uri}")
		String uri;
		@Value("${username}")
		String user;
		@Value("${password}")
		String password;
		@Value("${redisHost}")
		String redisHost;
		

		private RBucket<Neo4jConceptModel> bucket;

		public RBucket<Neo4jConceptModel> getBucket() {
			return bucket;
		}

		public void setBucket(RBucket<Neo4jConceptModel> bucket) {
			this.bucket = bucket;
		}

		@Override
		public void onApplicationEvent(ContextRefreshedEvent arg0) {
			// TODO Auto-generated method stub

			System.out.println("ApplicationListener Invoked At Spring Container Startup for ConceptList bcsbjcbscs");
			Neo4jConceptModel neoModel = new Neo4jConceptModel();
			String Query1 = "Match(n:concept)-[:SubConceptOf*]->(d:Domain) return n.name as concept,d.name as domain";
			
			Config config = new Config();
			config.useSingleServer().setAddress(redisHost);
			RedissonClient redisson = Redisson.create(config);

			System.out.println("config working");
			bucket = redisson.getBucket("neoModel");
			Map<String,ArrayList<String>> conceptList = new HashMap<>();
			driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
			Session session1 = driver.session();
			String Message1 = session1.writeTransaction(new TransactionWork<String>() {
				public String execute(Transaction tx) {
					StatementResult result = tx.run(Query1);
					while (result.hasNext()) {

						Record record = result.next();
						String concept = record.get("concept").asString();
						String domain = record.get("domain").asString();
						if(conceptList.containsKey(domain)){
							ArrayList<String> tempList = conceptList.get(domain);
							tempList.add(concept);
						}else{
							ArrayList<String> newList = new ArrayList<>();
							conceptList.put(domain,newList);
						}
					}
					neoModel.setConceptList(conceptList);
					return "Neo4j ConceptList Working";
				}
			});

			bucket.set(neoModel);
		}
		
	}

