package com.stackroute.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.stackroute.model.FetchUrl;
import com.stackroute.model.UserInput;


@Service
public class FetchService implements AutoCloseable {

	private  Driver driver;

	@Value("${uri}")
	String uri="bolt://172.23.238.152";
	@Value("${username}")
	String user="neo4j";
	@Value("${password}")
	String password="password";

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		driver.close();
	}
	public ArrayList<FetchUrl> fetchedUrl(String Query){
		driver = GraphDatabase.driver(uri,AuthTokens.basic(user, password));
		JSONObject searchObject =new JSONObject();
		JSONArray joArray = new JSONArray();
		ArrayList<FetchUrl> fetchList = new ArrayList<>();
		Session session = driver.session();
		String Greeting= session.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query);
				while(result.hasNext())
				{
					FetchUrl fetchUrl = new FetchUrl();
					Record record= result.next();
					Gson gson = new Gson();
					JsonElement jsonElement = gson.toJsonTree(record.asMap());
					fetchUrl = gson.fromJson(jsonElement, FetchUrl.class);
					fetchList.add(fetchUrl);
				}
				return "Working";
			}
		});

		fetchList.sort(Comparator.comparing(FetchUrl::getConfidenceScore, (s1, s2) -> {
			return s2.compareTo(s1);
		}));
//		try {
//			for(int i=0; i<fetchList.size();i++){
//				System.out.println(fetchList.get(i).getUrl());
//				JSONObject jo = new JSONObject();
//				jo.put("url",fetchList.get(i).getUrl());
//				jo.put("imgCount",fetchList.get(i).getImgCount());
//				jo.put("videoCount",fetchList.get(i).getVideoCount());
//				jo.put("codeCount",fetchList.get(i).getCodeCount());
//				jo.put("counterIndicator",fetchList.get(i).getCounterIndicator());
//				jo.put("confidenceScore",fetchList.get(i).getConfidenceScore());
//				//				jo.put("titleUrl",fetchList.get(i).getTitleUrl());
//				//				jo.put("metaUrl",fetchList.get(i).getMetaUrl());
//				joArray.put(jo);
//
//			} 
//			searchObject.put("Results", joArray);
//			System.out.println(searchObject.toString());
			System.out.println(Greeting);


//		}catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return fetchList;

	}
//	public static void main(String...args){
//		UserInput userInput = new UserInput();
//		userInput.setConcept("Ajax");
//		userInput.setIntent("Advance");
//		FetchService test = new FetchService();
//		test.fetchedUrl("Match(n:url)-[x:"+userInput.getIntent()+"]->(c:concept{name:\""+userInput.getConcept()+"\""+"})return n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, x.confidenceScore as confidenceScore");
//
//	}

}