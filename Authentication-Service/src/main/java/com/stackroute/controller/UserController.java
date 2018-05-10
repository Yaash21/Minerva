package com.stackroute.controller;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.domain.User;
import com.stackroute.service.UserService;
import com.stackroute.utils.CredentialsVerifier;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CredentialsVerifier credentialsVerifier;

	@PostMapping(value = "/register")
	public ResponseEntity<?> addUser(@RequestBody final User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.addUser(user);

		JSONObject json = new JSONObject();
		try {
			json.put("message", "Successfully Registered");
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> loginUser(@RequestBody final User user) {
		final User fetchedUser = userService.searchByEmailId(user.getEmailId());
		String authResult = credentialsVerifier.verify(user, fetchedUser);

		return new ResponseEntity<String>(authResult, HttpStatus.OK);
	}

	@GetMapping(value = "/showAll")
	public ResponseEntity<List<User>> getAllDomainExperts() {
		final List<User> fetchedUsers = userService.searchByRole("Domain Expert");

		return new ResponseEntity<List<User>>(fetchedUsers, HttpStatus.OK);
	}

	@PostMapping(value = "/delete")
	public ResponseEntity<String> deleteDomainExpert(@RequestBody final User user) {
		final User fetchedUser = userService.searchByEmailId(user.getEmailId());
		final String responseMessage = userService.deleteDomainExpert(fetchedUser.getId());

		return new ResponseEntity<String>(responseMessage, HttpStatus.OK);
	}
}
