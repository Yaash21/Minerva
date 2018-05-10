package com.stackroute.DialogManager.domain;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recommendations {

	private ArrayList<RecommendUrl> intentRecommendations;
	private ArrayList<RecommendUrl> subConceptRecommendations;
	private ArrayList<RecommendUrl> relatedConceptRecommendations;
	private String sessionId;

}
