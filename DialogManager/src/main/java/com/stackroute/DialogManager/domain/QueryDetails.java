package com.stackroute.DialogManager.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDetails {

	private String sessionId;
	private String concept;
	private String domain;
	private String query;
	private String intent;
	private String responseMessage;

}
