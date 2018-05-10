package com.stackroute.DialogManager.service;

import org.springframework.stereotype.Service;

import com.stackroute.DialogManager.api.AIConfiguration;
import com.stackroute.DialogManager.api.AIDataService;
import com.stackroute.DialogManager.api.model.AIRequest;
import com.stackroute.DialogManager.api.model.AIResponse;
import com.stackroute.DialogManager.domain.QueryDetails;

/**
 * Text client reads requests line by line from stdandart input.
 */
@Service
public class TextClientService {

	public TextClientService() {
		this.dataService = new AIDataService(configuration);
	}

	private static final String INPUT_PROMPT = "> ";

	private static AIConfiguration configuration = new AIConfiguration("f772f62697be4c91b444ded811c89d09");
	private AIDataService dataService = new AIDataService(configuration);

	int context = 0;
	String requestString = "";

	public QueryDetails responseEmmitter(String requestMessage, String sessionId) {

		QueryDetails queryDetails = new QueryDetails();

		System.out.print(INPUT_PROMPT);

		String responseMessage = "";
		try {

			AIRequest request = new AIRequest(requestMessage);
			AIResponse response = this.dataService.request(request);

			responseMessage = response.getResult().getFulfillment().getSpeech();

			if (response.getStatus().getCode() == 200) {

				if (!response.getResult().getContexts().isEmpty()) {
					if (context == 0 || context == 2) {
						context = 1;
						requestString = requestMessage;
					} else
						requestString = requestString + " " + requestMessage;

				} else if (context == 1) {
					context = 2;
					requestString = requestString + " " + requestMessage;

				} else if (context == 2 || context == 0) {
					context = 0;
					requestString = requestMessage;

				}

				try {
					queryDetails.setConcept(response.getResult().getStringParameter("Concept"));
				} catch (IllegalStateException e) {

				}
				try {
					queryDetails.setDomain(response.getResult().getStringParameter("Domain"));
				} catch (IllegalStateException e) {

				}

				queryDetails.setIntent(response.getResult().getMetadata().getIntentName());
				queryDetails.setResponseMessage(responseMessage);
				queryDetails.setSessionId(sessionId);
				if (requestString.equals(""))
					queryDetails.setQuery(requestMessage);
				else
					queryDetails.setQuery(requestString);

				System.out.println(requestString);
			} else {
				System.err.println(response.getStatus().getErrorDetails());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.print(INPUT_PROMPT);
		return queryDetails;
	}

	public AIDataService getDataService() {
		return dataService;
	}

	public static void setConfiguration(AIConfiguration configuration) {
		TextClientService.configuration = configuration;
	}

	public void setDataService(AIDataService dataService) {
		this.dataService = dataService;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

}