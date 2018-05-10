package com.stackroute.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.stackroute.model.RecommendUrl;
import com.stackroute.model.RecommendationModel;
import com.stackroute.model.UserInput;

@Service
public class RecommendService implements AutoCloseable {

	@Autowired
	UserInput userInput;
	@Autowired
	RecommendationModel recommendationModel;
	private  Driver driver;
	private Random randomGenerator;

	@Value("${uri}")
	String uri;
	@Value("${username}")
	String user;
	@Value("${password}")
	String password;

	@Override
	public void close() throws Exception {
		driver.close();
	}
	public ArrayList<RecommendUrl> RecommendationSubConcept (String domain, String concept, String intent){
		userInput.setConcept(concept);
		userInput.setDomain(domain);
		userInput.setIntent(intent);
		

		String Query1="Match(n:url)-[x:"+userInput.getIntent()+"]->(m:concept)-[:SubConceptOf]->(c:concept{name:\""+userInput.getConcept()
		+ "\""
		+"}),(d:Domain{name:\""+userInput.getDomain()
		+ "\""
		+"})return n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator,x.name as intentRecommend ,x.confidenceScore as confidenceScore,m.name as conceptRecommend,d.name as domainRecommend";

		driver = GraphDatabase.driver(uri,AuthTokens.basic(user, password));

		String Query =" Match(n:Intent)-[:Phrase_of]->(p:Phrase) WHERE n.name = \""+userInput.getIntent()+"\""+" return p.name as phrase";
		ArrayList<String> phraseList=new ArrayList<>();
		Session session1 = driver.session();
		String Greeting1= session1.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query);
				while(result.hasNext())
				{
					Record record= result.next();
					phraseList.add(record.get("phrase").toString());
				}
				return "Working of Phrases";
			}
		});
		System.out.println(Greeting1);
		ArrayList<RecommendUrl> subconceptList = new ArrayList<>();
		Session session = driver.session();
		String Greeting= session.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query1);
				while(result.hasNext())
				{
					RecommendUrl recommendUrl = new RecommendUrl();
					Record record= result.next();
					Gson gson = new Gson();
					JsonElement jsonElement = gson.toJsonTree(record.asMap());
					recommendUrl = gson.fromJson(jsonElement, RecommendUrl.class);
					randomGenerator = new Random();
					int index = randomGenerator.nextInt(phraseList.size());
					String temp= phraseList.get(index).replaceAll("\"","") +" " + record.get("conceptRecommend").toString();
					String recommendationString =temp;
					System.out.println(recommendationString);
					recommendUrl.setRecommendationString(recommendationString);
					recommendUrl.setIntentRecommend(intent);
					subconceptList.add(recommendUrl);
				}
				return "Working of Sub-concept Recommendation";
			}
		});

//		subconceptList.sort(Comparator.comparing(RecommendUrl::getConfidenceScore, (s1, s2) -> {
//			return s2.compareTo(s1);
//		}));
		System.out.println(Greeting);
		return subconceptList;

	}

	public ArrayList<RecommendUrl> RecommendationRelated (String domain, String concept, String intent){
		userInput.setConcept(concept);
		userInput.setDomain(domain);
		userInput.setIntent(intent);

		String Query2="Match(n:url)-[x:"+userInput.getIntent()+"]->(m:concept)-[:Related]->(c:concept{name:\""+userInput.getConcept()
		+ "\""
		+"}),(d:Domain{name:\""+userInput.getDomain()
		+ "\""
		+"})return n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator,x.name as intentRecommend ,x.confidenceScore as confidenceScore,m.name as conceptRecommend,d.name as domainRecommend";

		driver = GraphDatabase.driver(uri,AuthTokens.basic(user, password));

		String Query =" Match(n:Intent)-[:Phrase_of]->(p:Phrase) WHERE n.name = \""+userInput.getIntent()+"\""+" return p.name as phrase";
		ArrayList<String> phraseList=new ArrayList<>();
		Session session1 = driver.session();
		String Greeting1= session1.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query);
				while(result.hasNext())
				{
					Record record= result.next();
					phraseList.add(record.get("phrase").toString());
				}
				return "Working of Phrases";
			}
		});
		System.out.println(Greeting1);

		ArrayList<RecommendUrl> relatedList = new ArrayList<>();
		Session session = driver.session();
		String Greeting= session.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query2);
				while(result.hasNext())
				{
					RecommendUrl recommendUrl = new RecommendUrl();
					Record record= result.next();
					Gson gson = new Gson();
					JsonElement jsonElement = gson.toJsonTree(record.asMap());
					recommendUrl = gson.fromJson(jsonElement, RecommendUrl.class);
					randomGenerator = new Random();
					int index = randomGenerator.nextInt(phraseList.size());
					String temp=phraseList.get(index).replaceAll("\"","") +" " +record.get("conceptRecommend").toString();
					String recommendationString =temp;
					System.out.println(recommendationString);
					recommendUrl.setRecommendationString(recommendationString);
					recommendUrl.setIntentRecommend(intent);
					relatedList.add(recommendUrl);
				}
				return "Working Related-concept of Recommendation";
			}
		});
//		relatedList.sort(Comparator.comparing(RecommendUrl::getConfidenceScore, (s1, s2) -> {
//			return s2.compareTo(s1);
//		}));
		System.out.println(Greeting);

		if(relatedList.isEmpty()){
			String QuerySub="Match(n:url)-[x:"+userInput.getIntent()+"]->(m:concept)-[:SubConceptOf]->(c:concept{name:\""+userInput.getConcept()
			+ "\""
			+"}),(d:Domain{name:\""+userInput.getDomain()
			+ "\""
			+"})return n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator,x.name as intentRecommend ,x.confidenceScore as confidenceScore,m.name as conceptRecommend,d.name as domainRecommend";

			Session session2 = driver.session();
			String Greeting2= session.writeTransaction(new TransactionWork<String>()
			{
				public String execute(Transaction tx)
				{
					StatementResult result = tx.run(QuerySub);
					while(result.hasNext())
					{
						RecommendUrl recommendUrl = new RecommendUrl();
						Record record= result.next();
						Gson gson = new Gson();
						JsonElement jsonElement = gson.toJsonTree(record.asMap());
						recommendUrl = gson.fromJson(jsonElement, RecommendUrl.class);
						randomGenerator = new Random();
						int index = randomGenerator.nextInt(phraseList.size());
						String temp=phraseList.get(index).replaceAll("\"","") +" " +record.get("conceptRecommend").toString();
						String recommendationString = temp;
						System.out.println(recommendationString);
						recommendUrl.setRecommendationString(recommendationString);
						recommendUrl.setIntentRecommend(intent);
						relatedList.add(recommendUrl);
					}
					return "Working Related-concept of Recommendation";
				}
			});
//			relatedList.sort(Comparator.comparing(RecommendUrl::getConfidenceScore, (s1, s2) -> {
//				return s2.compareTo(s1);
//			}));
			System.out.println(Greeting2);
		}

		return relatedList;
	}

	public ArrayList<RecommendUrl> RecommendationIntent (String domain, String concept, String intent){
		userInput.setConcept(concept);
		userInput.setDomain(domain);
		userInput.setIntent(intent);



		String Query3 = "Match(n:url)-[x:"+userInput.getIntent()+"]->(c:concept{name:\"" + userInput.getConcept()
		+ "\""
		+ "}),(d:Domain{name:\"" + userInput.getDomain()+ "\"" + "})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, x.name as intentRecommend,x.confidenceScore as confidenceScore,c.name as conceptRecommend,d.name as domainRecommend";

		driver = GraphDatabase.driver(uri,AuthTokens.basic(user, password));

		String Query =" Match(n:Intent)-[:Phrase_of]->(p:Phrase) WHERE n.name = \""+userInput.getIntent()+"\""+" return p.name as phrase";
		ArrayList<String> phraseList=new ArrayList<>();
		Session session1 = driver.session();
		String Greeting1= session1.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query);
				while(result.hasNext())
				{
					Record record= result.next();
					String phrase=record.get("phrase").toString();
					System.out.println(phrase);
					phraseList.add(phrase);
				}
				return "Working of Intent Phrases";
			}
		});

		System.out.println(Greeting1);

		ArrayList<RecommendUrl> intentList = new ArrayList<>();

		Session session = driver.session();
		String Greeting= session.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query3);
				while(result.hasNext())
				{
					RecommendUrl recommendUrl = new RecommendUrl();
					Record record= result.next();
					Gson gson = new Gson();
					JsonElement jsonElement = gson.toJsonTree(record.asMap());
					recommendUrl = gson.fromJson(jsonElement, RecommendUrl.class);
					randomGenerator = new Random();
					int index = randomGenerator.nextInt(phraseList.size());
					String temp = phraseList.get(index).replaceAll("\"","") +" " +userInput.getConcept();
					String recommendationString = temp;
					System.out.println(recommendationString);
					recommendUrl.setRecommendationString(recommendationString);
					recommendUrl.setIntentRecommend(intent);
					intentList.add(recommendUrl);
				}
				return "Working of Intent Recommendation";
			}
		});
//		intentList.sort(Comparator.comparing(RecommendUrl::getConfidenceScore, (s1, s2) -> {
//			return s2.compareTo(s1);
//		}));
		System.out.println(Greeting);
		return intentList;
	}
	public ArrayList<RecommendUrl> RecommendationParentNodes(String domain,String intent){
		String Query4="Match(c:concept)-[:SubConceptOf]->(d:Domain{name:\""+domain+"\"}) match(n:url)-[x:"+intent+"]->(c) return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, x.name as intentRecommend,x.confidenceScore as confidenceScore,c.name as conceptRecommend,d.name as domainRecommend";
		driver = GraphDatabase.driver(uri,AuthTokens.basic(user, password));

		String Query =" Match(n:Intent)-[:Phrase_of]->(p:Phrase) WHERE n.name = \""+intent+"\""+" return p.name as phrase";
		ArrayList<String> phraseList=new ArrayList<>();
		Session session1 = driver.session();
		String Greeting1= session1.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query);
				while(result.hasNext())
				{
					Record record= result.next();
					String phrase=record.get("phrase").toString();
					System.out.println(phrase);
					phraseList.add(phrase);
				}
				return "Working of Intent Phrases";
			}
		});

		System.out.println(Greeting1);
		ArrayList<RecommendUrl> intentList = new ArrayList<>();

		Session session = driver.session();
		String Greeting= session.writeTransaction(new TransactionWork<String>()
		{
			public String execute(Transaction tx)
			{
				StatementResult result = tx.run(Query4);
				while(result.hasNext())
				{
					RecommendUrl recommendUrl = new RecommendUrl();
					Record record= result.next();
					Gson gson = new Gson();
					JsonElement jsonElement = gson.toJsonTree(record.asMap());
					recommendUrl = gson.fromJson(jsonElement, RecommendUrl.class);
					randomGenerator = new Random();
					int index = randomGenerator.nextInt(phraseList.size());
					String temp = phraseList.get(index).replaceAll("\"","") +" " +record.get("conceptRecommend").asString();
					String recommendationString = temp;
					System.out.println(recommendationString);
					recommendUrl.setRecommendationString(recommendationString);
					recommendUrl.setIntentRecommend(intent);
					intentList.add(recommendUrl);
				}
				return "Working of Intent Recommendation";
			}
		});
//		intentList.sort(Comparator.comparing(RecommendUrl::getConfidenceScore, (s1, s2) -> {
//			return s2.compareTo(s1);
//		}));
		System.out.println(Greeting);
		return intentList;
		
	}

	public  RecommendationModel Recommendations(String domain,String concept,String intent,String sessionId){
		ArrayList<RecommendUrl> intentList = new ArrayList<>();
		try{
		if(intent.equals("Beginner") || intent.equals("Illustration")){
			intentList=RecommendationIntent(domain,concept,"Intermediate");

		}else if (intent.equals("Intermediate")){
			intentList=RecommendationIntent(domain,concept,"Advance");
		}else if (intent.equals("Advance")){
			intentList=RecommendationRelated(domain,concept,intent);
			if(intentList.isEmpty()){
				intentList=RecommendationSubConcept(domain,concept,intent);
				if(intentList.isEmpty()){
					intentList = RecommendationParentNodes(domain,intent);
					
				}
			}

		}
		}catch(Exception e){
			intentList=RecommendationIntent(domain,concept,"Beginner");
			System.out.println("Intent not found");
		}
		
		recommendationModel.setIntentRecommendations(intentList);

		ArrayList<RecommendUrl> subConceptList = new ArrayList<>();
		subConceptList=RecommendationSubConcept(domain,concept,intent);
		recommendationModel.setSubConceptRecommendations(subConceptList);

		ArrayList<RecommendUrl> relatedConceptList = new ArrayList<>();
		relatedConceptList=RecommendationRelated(domain, concept, intent);
		recommendationModel.setRelatedConceptRecommendations(relatedConceptList);

		recommendationModel.setSessionId(sessionId);
		return recommendationModel;



	}


	//		String Query2="Match(n:url)-[x:"+userInput.getIntent()+"]->(m:concept)-[:Related]->(c:concept{name:\""+userInput.getConcept()
	//		+ "\""
	//		+"}),(d:Domain{name:\""+userInput.getDomain()
	//		+ "\""
	//		+"})return n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator,x.name as intentRecommend ,x.confidenceScore as confidenceScore,m.name as conceptRecommend,d.name as domainRecommend";
	//		if (userInput.getIntent()=="Beginner"){
	//			String Query3 = "Match(n:url)-[x:Intermediate]->(c:concept{name:\"" + userInput.getConcept()
	//			+ "\""
	//			+ "}),(d:Domain{name:\"" + userInput.getDomain()+ "\"" + "})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, x.name as intentRecommend,x.confidenceScore as confidenceScore,c.name as conceptRecommend,d.name as domainRecommend";
	//		}else if((userInput.getIntent()=="Intermediate")){
	//			String Query3 = "Match(n:url)-[x:Advance]->(c:concept{name:\"" + userInput.getConcept()
	//			+ "\""
	//			+ "}),(d:Domain{name:\"" + userInput.getDomain()+ "\"" + "})return n.titleUrl as titleUrl,n.metaUrl as metaUrl,n.imgCount as imgCount,n.videoCount as videoCount,n.codeCount as codeCount,n.url as url,n.counterIndicator as counterIndicator, x.name as intentRecommend,x.confidenceScore as confidenceScore,c.name as conceptRecommend,d.name as domainRecommend";
	//		}else if((userInput.getIntent()=="Illustration")){
	//		}


}
