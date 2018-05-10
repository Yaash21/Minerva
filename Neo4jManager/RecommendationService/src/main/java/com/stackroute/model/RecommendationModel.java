package com.stackroute.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class RecommendationModel {
@Override
	public String toString() {
		return "RecommendationModel [intentRecommendations=" + intentRecommendations + ", subConceptRecommendations="
				+ subConceptRecommendations + ", relatedconceptRecommendations=" + relatedConceptRecommendations + "]";
	}
private ArrayList<RecommendUrl> intentRecommendations;
private ArrayList<RecommendUrl> subConceptRecommendations;
private ArrayList<RecommendUrl> relatedConceptRecommendations;
private String sessionId;
public String getSessionId() {
	return sessionId;
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
public ArrayList<RecommendUrl> getIntentRecommendations() {
	return intentRecommendations;
}
public void setIntentRecommendations(ArrayList<RecommendUrl> intentRecommendations) {
	this.intentRecommendations = intentRecommendations;
}
public ArrayList<RecommendUrl> getSubConceptRecommendations() {
	return subConceptRecommendations;
}
public void setSubConceptRecommendations(ArrayList<RecommendUrl> subConceptRecommendations) {
	this.subConceptRecommendations = subConceptRecommendations;
}
public ArrayList<RecommendUrl> getRelatedConceptRecommendations() {
	return relatedConceptRecommendations;
}
public void setRelatedConceptRecommendations(ArrayList<RecommendUrl> relatedConceptRecommendations) {
	this.relatedConceptRecommendations = relatedConceptRecommendations;
}


}
