package com.mediamath.bidder;

import com.mediamath.bidder.model.LabelRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class BidderMainConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidderMainConfig.class);

    @Value("${mysql.password}")
    private String mysqlPassword;

    @Value("${mysql.url}")
    private String mysqlUrl;

    @Bean
    public DataSource mangoDataSource() {
        String urlConnection = mysqlUrl;
        String username = "root";
        String schema = "label";
        int connectionTimeoutInSeconds = 10;
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(urlConnection + "/" + schema);
        config.setConnectionTimeout(connectionTimeoutInSeconds * 1000);
        config.setUsername(username);
        config.setPassword(mysqlPassword);
        config.setMaximumPoolSize(2);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        LOGGER.info("mysql config : jdbc url {}", config.getJdbcUrl());
        LOGGER.info("mysql config : jdbc connectionTimeout {}", config.getConnectionTimeout());
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource mangoDataSource) {
        return new JdbcTemplate(mangoDataSource);
    }

    @Bean
    public HttpClientService delphiHttpClientService() {
        return new HttpClientService(200, "http://localhost:9222");
    }

    @Bean
    public BidderService BidderService(LabelRepository labelRepository) {
        return new BidderService(labelRepository, delphiHttpClientService());
    }

}
