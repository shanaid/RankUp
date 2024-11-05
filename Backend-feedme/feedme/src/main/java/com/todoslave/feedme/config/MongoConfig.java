package com.todoslave.feedme.config;

import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Override
  public com.mongodb.client.MongoClient mongoClient() {
    return MongoClients.create(mongoUri);
  }

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    logger.info("Creating MongoTemplate bean");
    MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoUri);
    return new MongoTemplate(factory);
  }
}
