//package com.stackroute.services;
//
//
//import static org.junit.Assert.*;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.amqp.AmqpException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.stackroute.domain.Domain;
//import com.stackroute.listener.Producer;
//import com.stackroute.listener.Receiver;
//import com.stackroute.service.VideoService;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class VideoCrawlerIntegrationTest {    
//	
//	@Autowired
//    private Receiver receive ;
//    @Autowired
//    private Domain domain;
//    @Autowired
//    private VideoService videoService;
//    
//    @Mock
//    private Producer producer;
//    
//	@Before
//	public void setupMock() throws AmqpException, IOException {
//		MockitoAnnotations.initMocks(this);
//		videoService.setProducer(producer);
//	}
//	
//    @Test
//    public void  testVideoCount() throws IOException{
////        Arrange
//        receive.receiveMessage("{\"domain\":\"Java\",\"concept\":\"Algorithm\",\"date\":\"Mar 23, 2018 12:33:51 PM\",\"urls\":[\"https://www.javatpoint.com\"]}");
//        Gson gson = new Gson();
//        Type type = new TypeToken<Domain>() {}.getType();
//        
//            domain = gson.fromJson(receive.getMessage(), type);
//            
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("VideoCount",0 );
//            obj.put("url","https://www.javatpoint.com");
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        List<JSONObject> objActual = new ArrayList<JSONObject>() ;
//        objActual.add(obj);
////        Act
//        List<JSONObject> objExpected = videoService.videoCount(domain);
//        String actual = objActual.get(0).toString();
//        String expected=objExpected.get(0).toString();
//    
////        Assert
//        assertEquals(expected.toString(),actual.toString());
//        
//        
//        
//    }
//    } 
