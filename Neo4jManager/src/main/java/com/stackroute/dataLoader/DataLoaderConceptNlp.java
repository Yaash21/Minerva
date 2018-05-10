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
import org.springframework.stereotype.Component;

import com.stackroute.redisson.ConceptNlpModel;
import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.Neo4jConceptNlpModel;


/**
 * The class gets all the list of concepts from Neo4j and puts to redis bucket "conceptNlpModel".
 * The data structure used here is Arraylist<String>.
 * Driver class is used to connect to Neo4j.
 * Transaction class is used to run the query.
 * @author yaash
 *
 */

@Component
public class DataLoaderConceptNlp implements ApplicationListener<ContextRefreshedEvent>  {


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
	private Neo4jConceptNlpModel neo4jConceptNlpModel;

	private RBucket<Neo4jConceptNlpModel> bucket;



	public RBucket<Neo4jConceptNlpModel> getBucket() {
		return bucket;
	}



	public void setBucket(RBucket<Neo4jConceptNlpModel> bucket) {
		this.bucket = bucket;
	}



	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		String Query = "match(n:concept)-[:SubConceptOf*]->(m:Domain) return m.name as domain,n.name as concept";

		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);

		bucket = redisson.getBucket("conceptNlpModel");
		ArrayList<ConceptNlpModel> conceptList = new ArrayList<>();

		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
		Session session1 = driver.session();
		String Message1 = session1.writeTransaction(new TransactionWork<String>() {
			public String execute(Transaction tx) {
				StatementResult result = tx.run(Query);
				while (result.hasNext()) {
					Record record = result.next();
					ConceptNlpModel conceptNlpModel = new ConceptNlpModel();
					conceptNlpModel.setConcept(record.get("concept").asString());
					conceptNlpModel.setDomain(record.get("domain").asString());
					conceptList.add(conceptNlpModel);
				}
				neo4jConceptNlpModel.setConceptList(conceptList);
				return "Neo4j ConceptList Working";
			}
		});
		bucket.set(neo4jConceptNlpModel);

	}

}
