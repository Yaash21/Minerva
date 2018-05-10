package com.stackroute.service;

import java.util.List;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stackroute.domain.Concept;
import com.stackroute.redisson.ConceptNlpModel;
import com.stackroute.redisson.IntentModel;
import com.stackroute.redisson.Neo4jConceptNlpModel;
import com.stackroute.redisson.Neo4jIntentModel;
import com.stackroute.redisson.Neo4jParentNlpModel;
import com.stackroute.repository.FetchConceptIntentRepository;

@Service
public class FetchConceptIntentService {

	@Value("${redisuri}")
	public String rediUrl;

	@Autowired
	private FetchConceptIntentRepository fetchConceptIntentRepository;

	// @Autowired
	// public void setFetchConceptIntentRepository(FetchConceptIntentRepository
	// fetchConceptIntentRepository) {
	// this.fetchConceptIntentRepository = fetchConceptIntentRepository;
	// }

	List<Concept> concepts;

	public List<ConceptNlpModel> fetchConcepts() {
		// concepts = fetchConceptIntentRepository.getConcepts();
		Config config = new Config();
		config.useSingleServer().setAddress(rediUrl);
		RedissonClient redisson = Redisson.create(config);
		RBucket<Neo4jConceptNlpModel> bucket = redisson.getBucket("conceptNlpModel");
		List<ConceptNlpModel> concepts = bucket.get().getConceptList();
		return concepts;
	}

	public List<IntentModel> fetchIntents() {
		Config config = new Config();
		config.useSingleServer().setAddress(rediUrl);
		RedissonClient redisson = Redisson.create(config);
		RBucket<Neo4jIntentModel> bucket = redisson.getBucket("intentModel");
		List<IntentModel> intents = bucket.get().getIntentList();
		return intents;
	}

	public Map<String, List<String>> fetchParentNodes() {
		Config config = new Config();
		config.useSingleServer().setAddress(rediUrl);
		RedissonClient redisson = Redisson.create(config);
		RBucket<Neo4jParentNlpModel> bucket = redisson.getBucket("parentNlpModel");
		Map<String, List<String>> parentNodes = bucket.get().getParentNodes();
		return parentNodes;
	}

}
