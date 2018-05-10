package com.stackroute.DialogManager;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.DialogManager.messaging.Receiver;

@EnableDiscoveryClient
@SpringBootApplication
public class DialogManagerApplication {

	public String NLPQueue = "nlp-service-queue";
	public String URLQueue = "user-search-result-queue";
	public String RecommendationQueue = "recommendations.queue";
	public String publishQueue = "dialogflow-service-queue";

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(NLPQueue);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	SimpleMessageListenerContainer container2(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter1) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(URLQueue);
		container.setMessageListener(listenerAdapter1);
		return container;
	}

	@Bean
	SimpleMessageListenerContainer container3(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapterForRecommender) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(RecommendationQueue);
		container.setMessageListener(listenerAdapterForRecommender);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	MessageListenerAdapter listenerAdapter1(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage1");
	}

	@Bean
	MessageListenerAdapter listenerAdapterForRecommender(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage2");
	}

	public static void main(String[] args) {
		SpringApplication.run(DialogManagerApplication.class, args);
	}

}

@RestController
@RefreshScope
class RefeshController {

}
