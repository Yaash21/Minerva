package com.stackroute.dataLoader;

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
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.stackroute.redisson.ConceptNlpModel;
import com.stackroute.redisson.Neo4jConceptNlpModel;
import com.stackroute.redisson.Neo4jParentNlpModel;

@Service
public class DataLoaderParentNlp implements ApplicationListener<ContextRefreshedEvent> {

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
	private Neo4jParentNlpModel neo4jParentNlpModel;

	private RBucket<Neo4jParentNlpModel> bucket;
	
	
	public RBucket<Neo4jParentNlpModel> getBucket() {
		return bucket;
	}


	public void setBucket(RBucket<Neo4jParentNlpModel> bucket) {
		this.bucket = bucket;
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("ApplicationListener Invoked At Spring Container Startup for ParentList for NLP");
		String Query = "match(n:concept)-[:SubConceptOf]->(m:Domain) return n.name as parent";
		
		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);
		
		bucket = redisson.getBucket("parentNlpModel");
		ArrayList<String> parentNodes = new ArrayList<>();
		
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		Session session1 = driver.session();
		String Message1 = session1.writeTransaction(new TransactionWork<String>() {
			public String execute(Transaction tx) {
				StatementResult result = tx.run(Query);
				while (result.hasNext()) {

					Record record = result.next();
					parentNodes.add(record.get("parent").asString());
					
				}
				neo4jParentNlpModel.setParentNodes(parentNodes);;
				return "Neo4j ConceptList Working";
			}
		});
		
		bucket.set(neo4jParentNlpModel);

		
	}

}
