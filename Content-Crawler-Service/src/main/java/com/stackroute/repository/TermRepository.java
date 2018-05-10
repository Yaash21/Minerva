package com.stackroute.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.domain.Word;


/**
 * @author 
 */
@Repository
public interface TermRepository extends PagingAndSortingRepository<Word, Integer> {

	@Query("MATCH (a:Word)-[:Is_A]->(b:Intent)-[:Is_An]->(:Root) RETURN a.name")
	List<String> graph();
}
