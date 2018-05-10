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
	

	
	public String getIntentUrl() {
		return intentUrl;
	}
	public void setIntentUrl(String intentUrl) {
		this.intentUrl = intentUrl;
	}
	public String getConceptName() {
		return conceptName;
	}
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
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
	
	public String getUrl() {
		return url;
	}
	public int getCounterIndicator() {
		return counterIndicator;
	}
	public void setCounterIndicator(int counterIndicator) {
		this.counterIndicator = counterIndicator;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getImgCount() {
		return imgCount;
	}
	public void setImgCount(Integer imgCount) {
		this.imgCount = imgCount;
	}
	public Integer getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(Integer videoCount) {
		this.videoCount = videoCount;
	}
	public Integer getCodeCount() {
		return codeCount;
	}
	public void setCodeCount(Integer codeCount) {
		this.codeCount = codeCount;
	}
	public double getConfidenceScore() {
		return confidenceScore;
	}
	public void setConfidenceScore(double confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	

}
