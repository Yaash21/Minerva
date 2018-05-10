package com.stackroute.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.domain.IncomingFormat;
import com.stackroute.domain.NeoFetch;
import com.stackroute.rabbitmq.Publisher;

@Service
public class MainService {

	@Autowired
	AmqpTemplate amqpTemplate;

	Publisher publisher;

	@Autowired
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	ConfidenceScoreService confidenceScoreService;

	@Autowired
	public void setConfidenceScoreService(ConfidenceScoreService confidenceScoreService) {
		this.confidenceScoreService = confidenceScoreService;
	}

	public List<JSONObject> getConfidenceScore(IncomingFormat format) {

		JSONObject obj = new JSONObject();

		/**
		 * objList contains input from orchestration service with all terms and
		 * its scores
		 */
		List<JSONObject> objList = new ArrayList<JSONObject>();
		/**
		 * neofetch list contains json object of intent, word and its weight in
		 * intent graph
		 */
		List<NeoFetch> neoFetchList = confidenceScoreService.graph();
		// System.out.println(neoFetchList);

		JSONObject terms = format.getTerms();

		Set keys = terms.keySet();
		Iterator a = keys.iterator();
		Double confidenceScore = 0.0;
		Double beginnnerCount = 0.0;
		Double intermediateCount = 0.0;
		Double advancedCount = 0.0;
		Double illustrationCount = 0.0;
		Double sampleSpaceCount = 0.0;
		Double probBeginner = 0.0;
		Double probIntermediate = 0.0;
		Double probAdvance = 0.0;
		Double probIllustration = 0.0;
		Double confidenceScoreBeginner = 0.0;
		Double confidenceScoreIntermediate = 0.0;
		Double confidenceScoreAdvance = 0.0;
		Double confidenceScoreIllustration = 0.0;

		String intent = "";
		List<JSONObject> jsonList = new ArrayList<JSONObject>();

		while (a.hasNext()) {
			String key = (String) a.next();
			// loop to get the dynamic key
			double value = (Double) terms.get(key);
			key = key.toLowerCase().trim();

			if (value != 0) {
				// System.out.println("In the incoming service"+key+" "+value);
				for (NeoFetch neo : neoFetchList) {
					String neoName = neo.getName().toLowerCase().trim();
					if (neoName.toString().equals(key)) {
						// System.out.println("In neo4j"+neo.getName()+"
						// "+neo.getWeight());
						if (neo.getIntent().equals("Beginner")) {
							beginnnerCount = beginnnerCount + (value * Integer.parseInt(neo.getWeight()));
						} else if (neo.getIntent().equals("Intermediate")) {
							intermediateCount = intermediateCount + (value * Integer.parseInt(neo.getWeight()));
						}

						else if (neo.getIntent().equals("Advance")) {
							advancedCount = advancedCount + (value * Integer.parseInt(neo.getWeight()));
						} else {
							illustrationCount = illustrationCount + (value * Integer.parseInt(neo.getWeight()));
						}

						sampleSpaceCount = sampleSpaceCount + (value * Integer.parseInt(neo.getWeight()));
						// System.out.println(sampleSpaceCount);
					}
				}
			}
		}

		probBeginner = (double) (beginnnerCount / sampleSpaceCount);
		probIntermediate = (double) (intermediateCount / sampleSpaceCount);
		probAdvance = (double) (advancedCount / sampleSpaceCount);
		probIllustration = (double) (illustrationCount / sampleSpaceCount);
		// System.out.println("Beginner prob="+probBeginner);
		// System.out.println("Intermediate prob="+probIntermediate);
		// System.out.println("Advanced prob="+probAdvance);
		// System.out.println("Illustration prob"+probIllustration);

		confidenceScoreBeginner = probBeginner * beginnnerCount;
		confidenceScoreIntermediate = probIntermediate * intermediateCount;
		confidenceScoreAdvance = probAdvance * advancedCount;
		confidenceScoreIllustration = probIllustration * illustrationCount;

		//
		// if((probBeginner>probIntermediate)&&(probBeginner>probAdvance)&&(probBeginner>probIllustration))
		// {
		// confidenceScore=probBeginner;
		// intent="Beginner";
		// }
		//
		// else
		// if((probIntermediate>probBeginner)&&(probIntermediate>probAdvance)&&(probIntermediate>probIllustration))
		// {
		// confidenceScore=probIntermediate;
		// intent="Intermediate";
		// }
		//
		// else
		// if((probAdvance>probBeginner)&&(probAdvance>probIntermediate)&&(probAdvance>probIllustration))
		// {
		// confidenceScore=probAdvance;
		// intent="Advance";
		// }
		//
		// else
		// if((probIllustration>probBeginner)&&(probIllustration>probIntermediate)&&(probIllustration>probAdvance))
		// {
		// confidenceScore=probIllustration;
		// intent="Illustration";
		// }
		//
		// if(intent.equalsIgnoreCase("Beginner"))
		// {
		// confidenceScoreFinal=probBeginner*beginnnerCount;
		// }
		//
		// else if(intent.equalsIgnoreCase("Intermediate"))
		// {
		// confidenceScoreFinal=probIntermediate*intermediateCount;
		// }
		//
		// else if(intent.equalsIgnoreCase("Advance"))
		// {
		// confidenceScoreFinal=probAdvance*advancedCount;
		// }
		//
		// else
		// {
		// confidenceScoreFinal=probIllustration*illustrationCount;
		// }
		//
		// System.out.println("Confidence score="+confidenceScore);
		//
		// obj.put("domainName",format.getDomain());
		// obj.put("conceptName",format.getConcept());
		// obj.put("url",format.getUrl());
		// obj.put("confidenceScore",confidenceScoreFinal);
		// obj.put("intent", intent);
		// obj.put("imageCount", format.getImgCount());
		// obj.put("videoCount", format.getVideoCount());
		// obj.put("codeCount", format.getCodeCount());
		// obj.put("counterIndicator", 1);

		// publisher.produceMsg(obj);
		// objList.add(obj);

		List<Double> probList = new ArrayList<Double>();
		probList.add(confidenceScoreBeginner);
		probList.add(confidenceScoreIntermediate);
		probList.add(confidenceScoreAdvance);
		probList.add(confidenceScoreIllustration);

		List<String> intentList = new ArrayList<String>();
		intentList.add("Beginner");
		intentList.add("Intermediate");
		intentList.add("Advance");
		intentList.add("Illustration");

		for (int i = 0; i < probList.size(); i++) {
			// System.out.println(probList.get(i));

			obj.put("domainName", format.getDomain());
			obj.put("conceptName", format.getConcept());
			obj.put("url", format.getUrl());
			obj.put("confidenceScore", probList.get(i));
			obj.put("intent", intentList.get(i));
			obj.put("imageCount", format.getImgCount());
			obj.put("videoCount", format.getVideoCount());
			obj.put("codeCount", format.getCodeCount());
			obj.put("counterIndicator", 1);
			obj.put("titleUrl", format.getTitleUrl());
			obj.put("metaUrl", format.getMetaUrl());
			publisher.produceMsg(obj);
			objList.add(obj);

		}
		return objList;

	}
}
