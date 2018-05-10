package com.stackroute.domain;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter @Setter
public class Result {
	
	private String domain;
	private String concept;
	private Date date;
	private List<String> urls;
	
}
