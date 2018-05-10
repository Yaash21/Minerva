package com.stackroute;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.rabbitmq.listener.MessageListener;
import com.stackroute.service.TermService;


/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.stackroute.domain")
public class ContentCrawlerServiceApplication {

	public final static String SFG_MESSAGE_QUEUE = "content-service-queue";
	public final static String PUBLISH_QUEUE = "crawler-service-queue";
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(SFG_MESSAGE_QUEUE);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageListener receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ContentCrawlerServiceApplication.class, args);
		context.getBean(TermService.class).graph();
	}
}

@RestController
@RefreshScope
class RefeshController {
	
	
}
