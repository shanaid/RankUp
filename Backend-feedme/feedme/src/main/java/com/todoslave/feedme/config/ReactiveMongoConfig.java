//package com.todoslave.feedme.config;
//
//import com.mongodb.client.MongoClients;
//import com.mongodb.reactivestreams.client.MongoClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
//
//@Configuration
//@EnableMongoAuditing
//@EnableReactiveMongoRepositories(basePackages = "com.todoslave.feedme.repository")
//public class ReactiveMongoConfig extends AbstractReactiveMongoConfiguration {
//
//    private static final Logger logger = LoggerFactory.getLogger(ReactiveMongoConfig.class);
//
//    @Value("${spring.data.mongodb.uri}")
//    private String mongoUri;
//
//    @Value("${spring.data.mongodb.database}")
//    private String databaseName;
//
//    @Override
//    public MongoClient reactiveMongoClient() {
//        return (MongoClient) MongoClients.create(mongoUri);
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return databaseName;
//    }
//}
