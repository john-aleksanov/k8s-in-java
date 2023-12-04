package com.epam.training.microservices.songservice.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Profile("postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("jdbc:postgresql://song-db:5432/song-service")
            .username("pg")
            .password("pg")
            .build();
    }

    @Bean
    @Profile("!postgres")
    public DataSource embeddedDataSource() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:file:~/song-service")
            .username("h2")
            .password("h2")
            .build();
    }
}
