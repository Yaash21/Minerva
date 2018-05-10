package com.stackroute.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.stackroute" })
@EnableMongoRepositories(basePackages = { "com.stackroute.repository" })
@EnableTransactionManagement
/**
 * Configuration class for spring-boot repository testing.
 * 
 * @author
 *
 */
public class RepositoryConfiguration {
}