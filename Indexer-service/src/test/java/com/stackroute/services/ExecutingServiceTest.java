package com.stackroute.services;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.domain.Confidence;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutingServiceTest {

	@Value("${uriofneo}")
	private String neo4juri;
	@Value("${spring.data.neo4j.username}")
	private String username;
	@Value("${spring.data.neo4j.password}")
	private String password;

	private PostRequest postRequest;
	@Value("${redisUri}")
	String redisUri;
	Confidence confidence = new Confidence();

	Driver driver;
	ExecutingService executingService;

	@Before
	public void setup() {
		driver = GraphDatabase.driver(neo4juri, AuthTokens.basic(username, password));
		// MockitoAnnotations.initMocks(this);

	}

	int nextCount = 0;
	int prevCount = 0;

	@Test
	public void test() {
		// Arrange
		confidence.setConceptName("SQL Query");
		confidence.setCodeCount(5);
		confidence.setConfidenceScore(34);
		confidence.setCounterIndicator(0);
		confidence.setDomainName("Java");
		confidence.setImageCount(25);
		confidence.setIntent("Beginner");
		confidence.setMetaUrl("This is test MetaUrl");
		confidence.setVideoCount(19);
		confidence.setTitleUrl("titleurl");
		confidence.setUrl("www.example.com");

		// Act
		try (Session session = driver.session()) {
			String greeting = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {

					StatementResult prevCountQuery = tx
							.run("Match (u:url)-[:" + confidence.getIntent() + "]->(c:concept{name:\""
									+ confidence.getConceptName() + "\"})-[:SubConceptOf*]->(d:Domain{name:\""
									+ confidence.getDomainName() + "\"}) return count(u) as Count");
					Record record = prevCountQuery.next();
					prevCount = record.get("Count").asInt();
					return "";

				}
			});
		}

		ExecutingService.executeQuery(confidence, neo4juri, username, password, redisUri);
		try (Session session1 = driver.session()) {
			String greeting1 = session1.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {

					StatementResult prevCountQuery = tx
							.run("Match (u:url)-[:" + confidence.getIntent() + "]->(c:concept{name:\""
									+ confidence.getConceptName() + "\"})-[:SubConceptOf*]->(d:Domain{name:\""
									+ confidence.getDomainName() + "\"}) return count(u) as Count");
					Record record = prevCountQuery.next();
					nextCount = record.get("Count").asInt();
					return "";

				}
			});

		}
		// Assert

		assertTrue(prevCount < nextCount);
		System.out.println(prevCount < nextCount);
	}

	@After
	public void tearDown() {
		try (Session session2 = driver.session()) {
			String greeting1 = session2.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {

					StatementResult prevCountQuery = tx.run("Match (u:url{url : \"" + confidence.getUrl() + "\"})-[:"
							+ confidence.getIntent() + "]->(c:concept{name:\"" + confidence.getConceptName()
							+ "\"})-[:SubConceptOf*]->(d:Domain{name:\"" + confidence.getDomainName()
							+ "\"}) detach delete u");
					postRequest = new PostRequest();
					postRequest.postMethod(confidence, redisUri);
					return "";

				}
			});

		}

	}
}