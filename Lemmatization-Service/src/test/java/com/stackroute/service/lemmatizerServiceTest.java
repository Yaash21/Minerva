package com.stackroute.service;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class lemmatizerServiceTest {
	
	LemmatizerService lemmatizerService;
	
	private String documentText;

	@Before
	public void setUp() throws Exception {
		lemmatizerService = new LemmatizerService();		
		documentText = "What is Ajax in java";
		
		
	}

	@Test
	public void test() {
		String actualResult = "what be Ajax in java";
		String retrievedResult = lemmatizerService.lemmatize(documentText);
		System.out.println(retrievedResult);
		assertEquals(retrievedResult.trim(), actualResult.trim());
	}

}
