package com.stackroute.services;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

import com.stackroute.domain.Confidence;

/**
 * create database connection with neo4j database, execute query to insert url
 * nodes into the concept-domain graph,inform neo4j manager about the graph
 * update
 */
public class ExecutingService implements AutoCloseable {
	private final Driver driver;
	private String redisUri;

	PostRequest postRequest;

	public ExecutingService(String uri, String username, String password, String redisUri) {

		driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
		this.redisUri = redisUri;
	}

	@Override
	public void close() throws Exception {
		driver.close();
	}

	private String createConfidenceNode(final Confidence confidence) {
		try (Session session = driver.session()) {
			String greeting = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {

					tx.run("MATCH(concept:concept{name:\"" + confidence.getConceptName()
							+ "\"})-[:SubConceptOf*]->(:Domain{name:\"" + confidence.getDomainName()
							+ "\"}) MERGE(i1:url{url:\"" + confidence.getUrl() + "\",imgCount:"
							+ confidence.getImageCount() + ",videoCount:" + confidence.getVideoCount() + ",codeCount:"
							+ confidence.getCodeCount() + ",titleUrl:\"" + confidence.getTitleUrl() + "\",metaUrl:\""
							+ confidence.getMetaUrl() + "\",counterIndicator:" + confidence.getCounterIndicator()
							+ "})-[:" + confidence.getIntent() + "{" + "confidenceScore:"
							+ confidence.getConfidenceScore() + "}" + "]->(concept)");
					// String ack = "{ \"Concept\":\"" + confidence.getConceptName() + "\",
					// \"Domain\":\""
					// + confidence.getDomainName() + "\" }";
					// RestTemplate restTemplate = new RestTemplate();
					// restTemplate.postForObject(redisUri, ack, String.class);

					postRequest = new PostRequest();
					postRequest.postMethod(confidence, redisUri);
					return "Inserted: " + confidence.toString();
				}
			});
			System.out.println(greeting);
			return greeting;
		}

	}

	public static void executeQuery(Confidence confidence, String uri, String username, String password,
			String redisUri) {
		try (ExecutingService greeter = new ExecutingService(uri, username, password, redisUri)) {

			greeter.createConfidenceNode(confidence);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPostRequest(PostRequest postRequest) {
		this.postRequest = postRequest;
	}
}