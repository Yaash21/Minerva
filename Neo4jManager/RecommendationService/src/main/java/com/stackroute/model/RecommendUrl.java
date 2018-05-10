package com.stackroute.model;

import org.springframework.stereotype.Component;

@Component
public class RecommendUrl {
	private String recommendationString;
	private String domainRecommend;
	private String conceptRecommend;
	private String intentRecommend;
	private String url;
	private int imgCount;
	private int videoCount;
	private int codeCount;
	private double confidenceScore;
	private int counterIndicator;
	private String titleUrl;
	private String metaUrl;
	private String sessionId;
	

	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRecommendationString() {
		return recommendationString;
	}
	public void setRecommendationString(String recommendationString) {
		this.recommendationString = recommendationString;
	}
	public String getDomainRecommend() {
		return domainRecommend;
	}
	public void setDomainRecommend(String domainRecommend) {
		this.domainRecommend = domainRecommend;
	}
	public String getConceptRecommend() {
		return conceptRecommend;
	}
	public void setConceptRecommend(String conceptRecommend) {
		this.conceptRecommend = conceptRecommend;
	}
	public String getIntentRecommend() {
		return intentRecommend;
	}
	public void setIntentRecommend(String intentRecommend) {
		this.intentRecommend = intentRecommend;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getImgCount() {
		return imgCount;
	}
	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}
	public int getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
	public int getCodeCount() {
		return codeCount;
	}
	public void setCodeCount(int codeCount) {
		this.codeCount = codeCount;
	}
	public double getConfidenceScore() {
		return confidenceScore;
	}
	public void setConfidenceScore(double confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	public int getCounterIndicator() {
		return counterIndicator;
	}
	public void setCounterIndicator(int counterIndicator) {
		this.counterIndicator = counterIndicator;
	}
	public String getTitleUrl() {
		return titleUrl;
	}
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
	public String getMetaUrl() {
		return metaUrl;
	}
	public void setMetaUrl(String metaUrl) {
		this.metaUrl = metaUrl;
	}
	

}
