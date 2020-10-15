package com.mediamath.delphi;

import com.mediamath.bidder.HttpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DelhpiMainConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelhpiMainConfig.class);

    @Bean
    public HttpClientService x() {
        return new HttpClientService(200, "http://localhost:9220");
    }

    @Bean
    public DelphiService delphiService() throws IOException {
        return new DelphiService();
    }
}
