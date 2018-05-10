package com.stackroute;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.stackroute.messaging.Receiver;

/**
 * 
 * main Class of Indexer Service ,intialise Beans for RabbitMq
 * 
 */
@EnableDiscoveryClient
@SpringBootApplication
public class IndexerServiceApplication {

	public final static String queueName = "confidence-queue";
	public final static String publishQueue = "garbage-queue";
	// public static final String topicExchangeName = "jsa.yaash";

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");

	}

	// @Bean
	// Queue queue() {
	// return new Queue(publishQueue, false);
	// }
	//
	// @Bean
	// TopicExchange exchange() {
	// return new TopicExchange(topicExchangeName);
	// }
	//
	// @Bean
	// Binding binding(Queue queue, TopicExchange exchange) {
	// return BindingBuilder.bind(queue).to(exchange).with(publishQueue);
	// }

	public static void main(String[] args) {
		SpringApplication.run(IndexerServiceApplication.class, args);
	}
}