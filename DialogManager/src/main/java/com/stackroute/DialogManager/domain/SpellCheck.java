package com.stackroute.DialogManager.domain;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class SpellCheck {

	String type;
	String sessionId;
	String parentNodes;
	String spelling;
	String message;

}
