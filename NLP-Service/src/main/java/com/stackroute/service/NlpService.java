package com.stackroute.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.domain.InputQuery;
import com.stackroute.publisher.Publisher;
import com.stackroute.redisson.ConceptNlpModel;
import com.stackroute.redisson.IntentModel;

@Service
public class NlpService {

	Publisher publisher;

	@Autowired
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	FetchConceptIntentService fetchConceptIntentService;

	@Autowired
	public void setFetchConceptIntentService(FetchConceptIntentService fetchConceptIntentService) {
		this.fetchConceptIntentService = fetchConceptIntentService;
	}

	String conceptFound = "";

	static int flag = 0;

	static String prevStr = "";

	static String currStr = "";

	Set<ConceptNlpModel> conceptsFound = new HashSet<ConceptNlpModel>();

	List<ConceptNlpModel> concepts = new ArrayList<>();
	List<String> recommendConcepts = new ArrayList<String>();
	String finalReturnCN;

	boolean check_ill = false;

	public JSONObject getConceptAndIntent(List<String> query, InputQuery inputQuery) {
		try {
			concepts = fetchConceptIntentService.fetchConcepts();
		} catch (Exception e) {
			System.out.println("Concepts not fetched from redis");
			return null;
		}
		List<String> queryList = new ArrayList<String>();
		JSONObject result = new JSONObject();
		String nouns = "";
		// String notNouns = "";
		String queryString = "";
		for (String word : query) {
			if (word.contains("NN") || word.contains("nn") || word.contains("JJ") || word.contains("jj")
					|| word.contains("vbn")) {
				String[] words = word.split("\\s+");
				// System.out.println(Arrays.toString(words));
				for (String str : words) {
					// System.out.println(str);
					int index = str.indexOf("/");
					queryList.add(str.substring(0, index));
					// str = str.toLowerCase();
					// str = str.substring(0,1).toUpperCase() +
					// str.substring(1).toLowerCase();
					nouns += str.substring(0, index) + " ";
					queryString += str.substring(0, index) + " ";
				}
			} else {
				int index = word.indexOf("/");
				queryList.add(word.substring(0, index));
				// notNouns += word.substring(0, index) + " ";
				queryString += word.substring(0, index) + " ";
			}
		}

		String concept = getConcept(queryString.trim());

		Set<String> finalConcepts = new HashSet<String>();

		String domain = "";

		for (ConceptNlpModel concept1 : conceptsFound) {
			domain = concept1.getDomain();
			int check = 0;
			for (ConceptNlpModel concept2 : conceptsFound) {
				if (!concept1.getConcept().equals(concept2.getConcept())
						&& concept2.getConcept().contains(concept1.getConcept()))
					check = 1;
			}
			if (check == 0)
				finalConcepts.add(concept1.getConcept());
		}

		// System.out.println("concepts" + finalConcepts.toString());

		String finalConcept = "";

		for (String str : finalConcepts) {
			if (!str.equalsIgnoreCase("java") && !str.equalsIgnoreCase("investment"))
				finalConcept = str;
			else
				domain = str;
		}

		if (inputQuery.getConcept() != null && !finalConcept.equalsIgnoreCase(inputQuery.getConcept()))
			finalConcept = inputQuery.getConcept();

		String intent = getIntent(queryString);
		// System.out.println(intent);

		if (domain.equals("")) {
			String[] input = queryString.split("\\s+");
			for (String str1 : input) {
				if (str1.equalsIgnoreCase("Java") || str1.equalsIgnoreCase("Investment"))
					domain = str1;
			}

		}

		try {

			if (finalConcept.isEmpty() && intent.equals("IN")) {
				// return ("CNIN" + "parent nodes");
				result = new JSONObject();

				Random random = new Random();
				String recommend = getRecommendations(nouns);
				Map<String, List<String>> parentNodes;

				try {
					parentNodes = fetchConceptIntentService.fetchParentNodes();
				} catch (Exception e) {
					System.out.println("Parent Concepts not fetched from redis");
					return null;
				}
				List<String> parentConcepts = new ArrayList<>();
				// System.out.println(parentNodes.get(domain).toString());
				if (!domain.equals("")) {
					parentConcepts = parentNodes.get(domain);
				} else {
					return null;
				}
				List<String> parentString = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					// random = new Random();
					int index = random.nextInt(parentConcepts.size());
					parentString.add(parentConcepts.get(index));
				}

				// if(recommend.equals("")){
				// finalReturnCN =
				// parentString.substring(0,parentString.length()-3);
				// }
				//
				// else{
				// finalReturnCN = recommend;
				// }

				// char s = parentString.charAt(parentString.length()-2);
				// parentString =
				// parentString.substring(0,parentString.length()-3);
				// String str = "Do you mean concepts: " + parentString;
				String conceptP = "";
				for (int i = 0; i < parentString.size(); i++) {
					if (i == parentString.size() - 1)
						conceptP += parentString.get(i);
					else
						conceptP += parentString.get(i) + ", ";
				}
				result.put("type", "CNIN");
				result.put("parentNodes", "You can look for: " + conceptP);
				result.put("sessionId", inputQuery.getSessionId());
				result.put("spelling", inputQuery.getSpelling());
				publisher.produceMsgToDialogFlow(result);

			} else if (!finalConcept.isEmpty() && intent.equals("IN")) {
				// return ("CYIN" + "Do you mean Beginner/Intermediate/Advanced
				// of "
				// + "" + finalConcepts.toString());
				result = new JSONObject();
				result.put("type", "CYIY");
				result.put("domain", domain);
				result.put("concept", finalConcept);
				// System.out.println("concept is" + inputQuery.getIntent());
				result.put("intent", inputQuery.getIntent());
				result.put("sessionId", inputQuery.getSessionId());
				result.put("illustration", check_ill);
				result.put("spelling", inputQuery.getSpelling());
				if (inputQuery.getSpelling().equals("correct")) {
					publisher.produceMsgToSearchService(result);
				} else {
					String queryCheck = "Do you mean this: " + queryString;

					result.put("message", queryCheck);
					publisher.produceMsgToSearchService(result);
					publisher.produceMsgToDialogFlow(result);
				}
			}

			else if (finalConcept.isEmpty() && !intent.equals("IN")) {
				// return ("CNIN" + "parent nodes");
				result = new JSONObject();
				Map<String, List<String>> parentNodes;
				try {
					parentNodes = fetchConceptIntentService.fetchParentNodes();
				} catch (Exception e) {
					System.out.println("Parent Concepts not fetched from redis");
					return null;
				}
				// System.out.println(parentNodes.get(domain).toString());
				List<String> parentConcepts = new ArrayList<>();
				// System.out.println(parentNodes.get(domain).toString());
				if (!domain.equals("")) {
					parentConcepts = parentNodes.get(domain);
				} else {
					return null;
				}
				String recommend = getRecommendations(nouns);
				Random random = new Random();
				List<String> parentString = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					// random = new Random();
					int index = random.nextInt(parentConcepts.size());
					parentString.add(parentConcepts.get(index));
				}

				// if(recommend.equals("")){
				// finalReturnCN =
				// parentString.substring(0,parentString.length()-3);
				// }
				//
				// else{
				// finalReturnCN = recommend;
				// }

				// char s = parentString.charAt(parentString.length()-2);
				// parentString =
				// parentString.substring(0,parentString.length()-3);
				// String str = "Do you mean concepts: " + parentString;
				String conceptP = "";
				for (int i = 0; i < parentString.size(); i++) {
					if (i == parentString.size() - 1)
						conceptP += parentString.get(i);
					else
						conceptP += parentString.get(i) + ", ";
				}
				result.put("spelling", inputQuery.getSpelling());
				result.put("type", "CNIY");
				result.put("parentNodes", "You can look for: " + conceptP);
				result.put("sessionId", inputQuery.getSessionId());
				publisher.produceMsgToDialogFlow(result);
			}

			else if (!finalConcept.isEmpty() && !intent.equals("IN")) {
				// return "CYIY");
				// System.out.println("Concept: " + finalConcepts.toString());
				// System.out.println("Intent: " + intent);
				result = new JSONObject();
				result.put("type", "CYIY");
				result.put("domain", domain);
				result.put("concept", finalConcept);
				result.put("intent", intent);
				result.put("sessionId", inputQuery.getSessionId());
				result.put("illustration", check_ill);
				result.put("spelling", inputQuery.getSpelling());
				if (inputQuery.getSpelling().equals("correct")) {
					publisher.produceMsgToSearchService(result);
				} else {
					String queryCheck = "Do you mean this: " + queryString;
					result.put("spelling", inputQuery.getSpelling());
					result.put("message", queryCheck);
					publisher.produceMsgToSearchService(result);
					publisher.produceMsgToDialogFlow(result);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		conceptsFound = new HashSet<ConceptNlpModel>();
		check_ill = false;
		conceptFound = "";

		return result;

	}

	public String getConcept(String nouns) {
		// List<String> concepts = conceptService.fetchConcepts();
		currStr = nouns;
		// List<String> concepts = new ArrayList<String>();
		// concepts.add("Spring Boot");
		// concepts.add("Spring");
		// concepts.add("app instances");
		// concepts.add("instances");

		if (flag == 0 && prevStr == currStr)
			return null;

		else {

			for (ConceptNlpModel concept : concepts) {
				// System.out.println(concept.getConcept()+"///");
				if (concept.getConcept().equalsIgnoreCase(nouns)) {

					conceptFound = concept.getConcept();
					flag = 1;
					// System.out.println("concept found" + conceptFound);
					conceptsFound.add(concept);

				}
			}
			if (flag == 0) {
				// System.out.println("//" + nouns + "ff");
				if (nouns.contains(" ")) {
					// System.out.println(nouns.substring(0, nouns.lastIndexOf("
					// ")));
					prevStr = nouns;
					getConcept(nouns.substring(0, nouns.lastIndexOf(" ")).trim());
					String[] arr = nouns.split(" ", 2);
					// System.out.println(arr[1]+"sss");//System.out.println(conceptFound);
					prevStr = nouns;
					getConcept(arr[1]);
				} else {
					prevStr = nouns;
					getConcept(nouns);
				}

			}
		}
		flag = 0;
		// concepts.add(conceptFound);

		return conceptFound;

		// System.out.println(conceptFound);

	}

	public String getIntent(String notNouns) {
		// List<Intent> intents = new ArrayList<Intent>();

		// int beginnerSum = 0;
		// int intermediateSum = 0;
		// int advancedSum = 0;
		// int illustrationSum = 0;

		// Intent intent1 = new Intent();
		//
		// intent1.setIntent("Beginner");
		// intent1.setName("how");
		// intent1.setWeight("9");
		//
		// Intent intent2 = new Intent();
		//
		// intent2.setIntent("Intermediate");
		// intent2.setName("define");
		// intent2.setWeight("7");
		//
		// Intent intent3 = new Intent();
		//
		// intent3.setIntent("Advance");
		// intent3.setName("analyze");
		// intent3.setWeight("8");
		//
		//
		// intents.add(intent1);
		// intents.add(intent2);
		// intents.add(intent3);

		List<IntentModel> intents;
		try {
			intents = fetchConceptIntentService.fetchIntents();
		} catch (Exception e) {
			System.out.println("Parent Concepts not fetched from redis");
			return null;
		}

		String[] intentArray = notNouns.split("\\s+");
		Map<String, Integer> weights = new HashMap<String, Integer>();

		for (String word : intentArray) {
			int sum = 0;
			for (IntentModel intent : intents) {
				if (word.equalsIgnoreCase(intent.getTerm())) {
					if (intent.getIntent().equals("Beginner")) {

						if (weights.containsKey("Beginner")) {
							sum = weights.get("Beginner");
							sum += Integer.parseInt(intent.getWeight());
							weights.put("Beginner", sum);
							System.out.println(word + " B:" + intent.getWeight());
						} else {
							weights.put("Beginner", Integer.parseInt(intent.getWeight()));
							System.out.println(word + " B:" + intent.getWeight());
						}
					}

					else if (intent.getIntent().equals("Intermediate")) {

						if (weights.containsKey("Intermediate")) {
							sum = weights.get("Intermediate");
							sum += Integer.parseInt(intent.getWeight());
							weights.put("Intermediate", sum);
							System.out.println(word + " I:" + intent.getWeight());
						} else {
							weights.put("Intermediate", Integer.parseInt(intent.getWeight()));
							System.out.println(word + " I:" + intent.getWeight());
						}
					}

					else if (intent.getIntent().equals("Advance")) {
						if (weights.containsKey("Advance")) {
							sum = weights.get("Advance");
							sum += Integer.parseInt(intent.getWeight());
							weights.put("Advance", sum);
							System.out.println(word + " A:" + intent.getWeight());
						} else {
							weights.put("Advance", Integer.parseInt(intent.getWeight()));
							System.out.println(word + " A:" + intent.getWeight());
						}
					}

					else if (intent.getIntent().equalsIgnoreCase("Illustration")) {
						check_ill = true;
						if (weights.containsKey("Illustration")) {
							sum = weights.get("Illustration");
							sum += Integer.parseInt(intent.getWeight());
							weights.put("Illustration", sum);
							System.out.println(word + " Il:" + intent.getWeight());
						} else {
							weights.put("Illustration", Integer.parseInt(intent.getWeight()));
							System.out.println(word + " Il:" + intent.getWeight());
						}
					}

				}
			}

		}

		int max = 0;
		String intent = "";
		Set keys = weights.keySet();
		Iterator a = keys.iterator();

		while (a.hasNext()) {
			String key = (String) a.next();
			int value = weights.get(key);
			System.out.println(key + " " + value);
			if (value > max) {
				max = value;
				intent = key;
			}
		}

		if (max == 0)
			return "IN";
		else
			return intent;
	}

	public String getRecommendations(String nouns) {
		String[] nounList = nouns.split("\\s+");

		String result = "";
		for (String str : nounList) {
			for (ConceptNlpModel concept1 : concepts) {

				if (concept1.getConcept().contains(str.trim()) && !str.equalsIgnoreCase("Java")) {
					System.out.println("////recommend" + recommendConcepts.add(concept1.getConcept()));
				}

			}
		}

		for (String str : recommendConcepts) {
			result += str + " ";
		}
		return result;
	}

	// public static void main(String[] args){
	// MainService mainService = new MainService();
	// mainService.getConcept("app instances Spring Boot works");
	//
	//
	//
	//
	//
	// System.out.println(mainService.getIntent("how define analyse"));
	// }

}
