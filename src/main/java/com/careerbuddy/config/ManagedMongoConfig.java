package com.careerbuddy.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ManagedMongoConfig extends AbstractMongoClientConfiguration {

    private final Environment environment;

    @Override
    protected String getDatabaseName() {
        return environment.getProperty("spring.data.mongodb.database");
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new ConnectionString(getConnectionString()));
    }

    private String getConnectionString() {
        return String.format(
                "mongodb://%s:%s@%s:%s/%s%s",
                environment.getProperty("spring.data.mongodb.managed.username"),
                environment.getProperty("spring.data.mongodb.managed.password"),
                environment.getProperty("spring.data.mongodb.managed.server"),
                environment.getProperty("spring.data.mongodb.managed.port"),
                environment.getProperty("spring.data.mongodb.database"),
                environment.getProperty("spring.data.mongodb.managed.parameters"));
    }
}
