package com.stackroute.exceptions;

import com.stackroute.domain.User;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException(User user) {
		super(user.getEmailId() + " is already present");
	}
}
