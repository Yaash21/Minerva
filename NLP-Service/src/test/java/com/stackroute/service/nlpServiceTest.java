package com.stackroute.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.stackroute.domain.InputQuery;
import com.stackroute.publisher.Publisher;
import com.stackroute.redisson.ConceptNlpModel;
import com.stackroute.redisson.IntentModel;

@RunWith(MockitoJUnitRunner.class)
public class nlpServiceTest {

	private NlpService nlpService;

	@Mock
	private FetchConceptIntentService fetchConceptIntentService;

	@Mock
	private Publisher publisher;

	private InputQuery inputQuery = new InputQuery();

	private List<ConceptNlpModel> concepts;

	private List<IntentModel> intents;

	private List<String> query;

	JSONObject expectedResult = new JSONObject();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		nlpService = new NlpService();
		nlpService.setPublisher(publisher);
		nlpService.setFetchConceptIntentService(fetchConceptIntentService);
		query = new ArrayList<>();
		query.add("what/wdt");
		query.add("is/vb");
		query.add("Ajax/nn");

		expectedResult.put("type", "CYIY");
		expectedResult.put("domain", "Java");
		expectedResult.put("concept", "Ajax");
		expectedResult.put("intent", "Beginner");
		expectedResult.put("sessionId", inputQuery.getSessionId());
		expectedResult.put("illustration", false);
		expectedResult.put("spelling", "correct");

		concepts = new ArrayList<>();
		ConceptNlpModel concept1 = new ConceptNlpModel();
		concept1.setConcept("Ajax");
		concept1.setDomain("Java");

		concepts.add(concept1);

		intents = new ArrayList<>();
		IntentModel intent = new IntentModel();
		intent.setIntent("Beginner");
		intent.setTerm("what");
		intent.setWeight("8");

		IntentModel intent1 = new IntentModel();
		intent1.setIntent("Beginner");
		intent1.setTerm("is");
		intent1.setWeight("8");

		intents.add(intent1);
		intents.add(intent);

		inputQuery.setDomain("Java");
		inputQuery.setConcept("Ajax");
		inputQuery.setIntent("Beginner");
		inputQuery.setSpelling("correct");

	}

	@Test
	public void testGetConceptAndIntent() throws Exception {

		// Arrange
		when(fetchConceptIntentService.fetchConcepts()).thenReturn(concepts);

		when(fetchConceptIntentService.fetchIntents()).thenReturn(intents);

		// Act
		final JSONObject actualResult = nlpService.getConceptAndIntent(query, inputQuery);

		// Assert
		System.out.println("Actual result " + actualResult.toString());
		System.out.println("Expected result " + expectedResult.toString());
		assertThat(actualResult.toString(), is(equalTo(expectedResult.toString())));

	}

}
