package com.mediamath.delphi;

import com.mediamath.adx.ExchangeMainConfig;
import com.mediamath.bidder.BidderMainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
@EnableAutoConfiguration(exclude={
        DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
@PropertySources({
//        @PropertySource(value = "classpath:dynamic-label-demo.properties"),
        @PropertySource(value = "file://${PROPERTY_FILE:/etc/dynamic-label-demo/dynamic-label-demo.properties}",
                ignoreResourceNotFound = true) })

@Import({DelhpiMainConfig.class})
@ComponentScan(basePackages = {
        "com.mediamath.bidder",
        "com.mediamath.delphi"
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BidderMainConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ExchangeMainConfig.class)
})
public class DelphiApp {
        public static void main(String[] args) {
            SpringApplication sa = new SpringApplication();

            sa.setSources(new HashSet<>(Collections.singletonList(DelphiApp.class.getName())));

            sa.setDefaultProperties(Collections
                    .singletonMap("server.port", "9222"));
            ConfigurableApplicationContext context = sa.run(args);
        }
}
