package com.stackroute.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.stackroute.domain.User;
import com.stackroute.exceptions.UserAlreadyExistsException;
import com.stackroute.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User addUser(User user) {

		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new UserAlreadyExistsException(user);
		}
		return user;
	}

	public User searchByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	public List<User> searchByRole(String role) {
		return userRepository.findByRole(role);
	}

	public String deleteDomainExpert(int id) {
		userRepository.deleteById(id);
		return "Domain Expert Deleted";
	}
}
