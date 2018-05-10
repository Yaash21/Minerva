package com.stackroute.service;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.Before;

public class PosTaggingServiceTest {
	
	private PosTaggingService posTaggingService;
	String document = "";

  @Before
  public void setUp() throws Exception {
	  
	  posTaggingService = new PosTaggingService();
	  document = "what be ajax in java";
  }
  
  @Test
  public void testPosTagging() throws Exception{
	  String expectedResult = posTaggingService.posTagging(document);
	  String actualResult = "What/wdt Be/vb Ajax/nn In/in Java/nn";
	  
	  assertEquals(expectedResult.toString(), actualResult.toString());
  }
}


