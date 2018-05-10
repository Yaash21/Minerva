package com.stackroute.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.domain.NeoFetch;
import com.stackroute.domain.Word;

/**
 * @author 
 */
@Repository
public interface RepositoryCS extends PagingAndSortingRepository<Word, Integer> {

	@Query("MATCH (a:Word)-[:Is_A]->(b:Intent)-[:Is_An]->(:Root) RETURN b.name as intent,a.name as name,a.weight as weight")
	List<NeoFetch> graph();
	
}

