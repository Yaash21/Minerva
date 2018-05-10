package com.stackroute.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.domain.Concept;
import com.stackroute.domain.NeoFetch;
import com.stackroute.domain.Word;

@Repository
public interface FetchConceptIntentRepository extends PagingAndSortingRepository<Word, Integer> {
	
	@Query("match(n:concept)-[:SubConceptOf*]->(m:Domain) return m.name as domain,n.name as concept")
	List<Concept> getConcepts();
	
	@Query("MATCH (a:Word)-[:Is_A]->(b:Intent)-[:Is_An]->(:Root) RETURN b.name as intent,a.name as name,a.weight as weight")
	List<NeoFetch> getIntents();
	
	@Query("match(n:concept)-[:SubConceptOf]->(m:Domain) return n.name")
	List<String> getParentNodes();
	

}
