package com.stackroute.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpellCheckServiceTest {
	
	private SpellCheckService spellCheckService;
	private String query;
	
	

	@Before
	public void setUp() throws Exception {
		
		spellCheckService = new SpellCheckService();
		query = "What is Sprin Framewor in java";
	}

	@Test
	public void test() {
		String actualResult = "What is Spring Framework in java";
		String retrievedResult = spellCheckService.calculateDistance(query);
		System.out.println(retrievedResult.toLowerCase());
		System.out.println(actualResult.toLowerCase());
		assertEquals(retrievedResult.toLowerCase().trim(), actualResult.toLowerCase().trim());
	}

}
