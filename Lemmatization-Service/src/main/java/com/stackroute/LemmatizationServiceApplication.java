package com.stackroute;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.receiver.Receiver;

@EnableDiscoveryClient
@SpringBootApplication
public class LemmatizationServiceApplication {

	/**
	 * Name of the queue to publish output
	 */
	@Value("${publishqueue}")
	public String PUBLISH_QUEUE;

	@Value("${subscribequeue}")
	public String SUBSCRIBE_QUEUE;

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(SUBSCRIBE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	/**
	 * Bean which receives message from message queue
	 * 
	 * @param receiver
	 * @return
	 */

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) {
		SpringApplication.run(LemmatizationServiceApplication.class, args);
	}
}

/**
 * Dummy Controller
 *
 */
@CrossOrigin
@RestController
@RefreshScope
class RefreshController {

}
