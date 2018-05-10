package com.stackroute;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.stackroute.service.Service;

/**
 * 
 * @author meghana
 *
 */
// @EnableDiscoveryClient
@SpringBootApplication
public class SpringBootHelloWorldApplication {

	@Value("${semantic.rabbitmq.queueImage}")
	String queueForImage;

	@Value("${semantic.rabbitmq.queueCode}")
	String queueForVideo;

	@Value("${semantic.rabbitmq.queueVideo}")
	String queueForCode;

	@Value("${semantic.rabbitmq.queueContent}")
	String queueForContent;

	@Value("${semantic.rabbitmq.exchange}")
	String exchange;

	@Value("${semantic.rabbitmq.routingkey}")
	private String routingkey;

	@Value("${lemmatize.subscribe}")
	String queueForLemmatizeSubscribe;

	@Value("${lemmatize.publish}")
	String queueForLemmatizePublish;

	@Value("${postagging.publish}")
	String queueForPostagging;

	@Value("${nlpservice.publish.dialog}")
	String queueForNlpPublishDialog;

	@Value("${nlpservice.publish.search}")
	String queueForNlpPublishSearch;

	@Value("${nlpservice.exchange}")
	String nlpExchange;

	@Value("${nlpservice.key}")
	private String nlpRoutingkey;

	@Value("${nlpservice.publish.recommend}")
	String queueForNlpRecommend;

	@Value("${recommendation.publish}")
	String queueForRecommendPublish;

	@Value("${search.publish}")
	String queueForSearchPublish;

	@Value("${crawlers.publish}")
	String queueForCrawlersPublish;

	@Value("${integration.publish}")
	String queueForIntegration;

	@Value("${confidence.publish}")
	String queueForConfidence;

	@Value("${indexer.publish}")
	String queueForIndexer;

	@Bean
	Queue queueImage() {
		return new Queue(queueForImage, false);
	}

	@Bean
	Queue queueVideo() {
		return new Queue(queueForVideo, false);
	}

	@Bean
	Queue queueCode() {
		return new Queue(queueForCode, false);
	}

	@Bean
	Queue queueContent() {
		return new Queue(queueForContent, false);
	}

	@Bean
	Queue queueForLemmatizeSubscribe() {
		return new Queue(queueForLemmatizeSubscribe, false);
	}

	@Bean
	Queue queueForLemmatizePublish() {
		return new Queue(queueForLemmatizePublish, false);
	}

	@Bean
	Queue queueForPostagging() {
		return new Queue(queueForPostagging, false);
	}

	@Bean
	Queue queueForNlpPublishDialog() {
		return new Queue(queueForNlpPublishDialog, false);
	}

	@Bean
	Queue queueForNlpPublishSearch() {
		return new Queue(queueForNlpPublishSearch, false);
	}

	@Bean
	Queue queueForNlpRecommend() {
		return new Queue(queueForNlpRecommend, false);
	}

	@Bean
	Queue queueForRecommendPublish() {
		return new Queue(queueForRecommendPublish, false);
	}

	@Bean
	Queue queueForSearchPublish() {
		return new Queue(queueForSearchPublish, false);
	}

	@Bean
	Queue queueForCrawlersPublish() {
		return new Queue(queueForCrawlersPublish, false);
	}

	@Bean
	Queue queueForIntegration() {
		return new Queue(queueForIntegration, false);
	}

	@Bean
	Queue queueForConfidence() {
		return new Queue(queueForConfidence, false);
	}

	@Bean
	Queue queueForIndexer() {
		return new Queue(queueForIndexer, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	DirectExchange nlpExchange() {
		return new DirectExchange(nlpExchange);
	}

	@Bean
	Binding bindingImage(Queue queueImage, DirectExchange exchange) {
		return BindingBuilder.bind(queueImage).to(exchange).with(routingkey);
	}

	@Bean
	Binding bindingVideo(Queue queueVideo, DirectExchange exchange) {
		return BindingBuilder.bind(queueVideo).to(exchange).with(routingkey);
	}

	@Bean
	Binding bindingCode(Queue queueCode, DirectExchange exchange) {
		return BindingBuilder.bind(queueCode).to(exchange).with(routingkey);
	}

	@Bean
	Binding bindingContent(Queue queueContent, DirectExchange exchange) {
		return BindingBuilder.bind(queueContent).to(exchange).with(routingkey);
	}

	@Bean
	Binding bindingNlpSearch(Queue queueForNlpPublishSearch, DirectExchange nlpExchange) {
		return BindingBuilder.bind(queueForNlpPublishSearch).to(nlpExchange).with(nlpRoutingkey);
	}

	@Bean
	Binding bindingNlpRecommend(Queue queueForNlpRecommend, DirectExchange nlpExchange) {
		return BindingBuilder.bind(queueForNlpRecommend).to(nlpExchange).with(nlpRoutingkey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootHelloWorldApplication.class, args);
		context.getBean(Service.class).send();

	}

}