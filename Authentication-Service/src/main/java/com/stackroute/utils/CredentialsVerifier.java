package com.stackroute.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.stackroute.domain.User;

@Component
public class CredentialsVerifier {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired 
	private TokenGenerator tokenGenerator;

	public String verify(User user, User fetchedUser){
		
		if(fetchedUser == null) {
			
			return createJson("Invalid EmailId", "");
		}
		
		if(!user.getRole().equals(fetchedUser.getRole())) {
			
			return createJson("Incorrect Role", "");
		}
		
		if(bCryptPasswordEncoder.matches(user.getPassword(),fetchedUser.getPassword())) {
			String token = tokenGenerator.addAuthentication(user.getEmailId());

			return createJson("success", token);
		}
		
		return createJson("Incorrect Password", "");
	}
	
	private String createJson(String result, String token) {
		
		JSONObject json = new JSONObject();

		try {
			json.put("AuthenticationResult", result);
			json.put("token", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
