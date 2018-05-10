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
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


import com.stackroute.messaging.Receiver;

@EnableDiscoveryClient
@SpringBootApplication
public class OrchestraServiceApplication {
	public final static String queueName = "crawler-service-queue";
	public final static String publishQueue = "integration";
	   public static final String topicExchangeName = "orchestra.exchange";

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
	 @Bean
	    Queue queue() {
	        return new Queue(publishQueue, false);
	    }

	    @Bean
	    TopicExchange exchange() {
	        return new TopicExchange(topicExchangeName);
	    }

	    @Bean
	    Binding binding(Queue queue, TopicExchange exchange) {
	        return BindingBuilder.bind(queue).to(exchange).with(publishQueue);
	    }
	   

	//    @Bean
	//    JedisConnectionFactory jedisConnectionFactory(){
	//    	return new JedisConnectionFactory();
	//    }
	//    
	//    @Bean
	//    RedisTemplate<String,Model> redisTemplate(){
	//    	RedisTemplate<String,Model> redisTemplate = new RedisTemplate<>();
	//    	redisTemplate.setConnectionFactory(jedisConnectionFactory());
	//		return redisTemplate;
	//    	
	//    }
	public static void main(String[] args) {
		SpringApplication.run(OrchestraServiceApplication.class, args);
	}
}
