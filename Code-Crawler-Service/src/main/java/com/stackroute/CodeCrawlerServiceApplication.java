package com.stackroute;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.listener.MessageListener;

/**
 * 
 * This is the main class of this SpringBootApplication.
 *
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
/**
 * 
 * @author vaishnavi
 *
 */
public class CodeCrawlerServiceApplication {
	/**
	 * Name of the exchange
	 */

	public String topicExchange = "integration.exchange";

	/**
	 * Name of the queue to fetch input
	 */

	@Value("${subscribequeue}")
	public String SUBSCRIBE_QUEUE;
	/**
	 * Name of the queue to publish output
	 */
	@Value("${publishqueue}")
	public String PUBLISH_QUEUE;

	/**
	 * Bean which creates container for Message Listener
	 * 
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
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
	MessageListenerAdapter listenerAdapter(MessageListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	/**
	 * 
	 * This is the main method.
	 */
	public static void main(String[] args) {
		SpringApplication.run(CodeCrawlerServiceApplication.class, args);
	}
}

/**
 * Dummy Controller
 *
 */
@RestController
@RefreshScope
class RefreshController {

}
