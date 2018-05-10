package com.stackroute.repository;

import org.springframework.stereotype.Repository;

import com.stackroute.domain.Concept;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ConceptRepository extends PagingAndSortingRepository<Concept, Integer> {

	@Query("MATCH (n:concept) RETURN n.name")
	List<String> getConcepts();
	
	@Query("MATCH (n:Word) RETURN n.name")
	List<String> getIntents();
}
