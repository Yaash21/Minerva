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

import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.Neo4jIntentModel;

/**
 * The class gets all the words in the Intent Graph with intent and weight from Neo4j and puts to redis bucket "intentModel".
 * The data structure used here is Arraylist<IntentModel>.
 * Driver class is used to connect to Neo4j.
 * Transaction class is used to run the query.
 * @author yaash
 *
 */

@Component
public class DataLoaderIntent implements ApplicationListener<ContextRefreshedEvent> {

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

	public RBucket<Neo4jIntentModel> getBucket() {
		return bucket;
	}

	public void setBucket(RBucket<Neo4jIntentModel> bucket) {
		this.bucket = bucket;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		String Query = "Match(w:Word)-[Is_A]->(i:Intent) return w.name as term, i.name as intent, w.weight as weight";

		Config config = new Config();
		config.useSingleServer().setAddress(redisHost);
		RedissonClient redisson = Redisson.create(config);

		bucket = redisson.getBucket("intentModel");
		ArrayList<IntentModel> intentList = new ArrayList<>();
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
				neo4jIntentModel.setIntentList(intentList);
				return "Neo4j ConceptList Working";
			}
		});
		bucket.set(neo4jIntentModel);

	}

}
