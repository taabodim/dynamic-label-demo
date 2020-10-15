package com.mediamath.adx;

import com.mediamath.bidder.HttpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ExchangeMainConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeMainConfig.class);

    @Bean
    public HttpClientService bidderHttpClientService() {
        return new HttpClientService(200, "http://localhost:9220");
    }

    @Bean
    public AdxExchange adxExchange() throws IOException {
        return new AdxExchange(bidderHttpClientService());
    }
}
