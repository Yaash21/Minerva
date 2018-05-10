package com.stackroute.DialogManager.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
