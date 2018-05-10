package com.stackroute.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {

	private String message;

	public ExceptionResponse(String message) {
		super();
		this.message = message;

	}
}
