package com.stackroute.redisson;

import org.springframework.stereotype.Component;

@Component
public class FetchUrl {
	
	private String url;
	private int imgCount;
	private int videoCount;
	private int codeCount;
	private double confidenceScore;
	private int counterIndicator;
	private String titleUrl;
	private String metaUrl;
	private String intentUrl;
	private String conceptName;
	private String domainName;
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getConceptName() {
		return conceptName;
	}
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
	public String getIntentUrl() {
		return intentUrl;
	}
	public void setIntentUrl(String intentUrl) {
		this.intentUrl = intentUrl;
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
