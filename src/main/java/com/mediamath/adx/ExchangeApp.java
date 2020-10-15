package com.mediamath.adx;

import com.mediamath.bidder.BidderMainConfig;
import com.mediamath.delphi.DelhpiMainConfig;
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
//@Import({EssentialMangoConfig.class, EssentialConfig.class, MainConfig.class})
@Import({ExchangeMainConfig.class})
@ComponentScan(basePackages = {
        "com.mediamath.adx"
},  excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DelhpiMainConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BidderMainConfig.class)
})
public class ExchangeApp {
        public static void main(String[] args) {
            SpringApplication sa = new SpringApplication();

            sa.setSources(new HashSet<>(Collections.singletonList(ExchangeApp.class.getName())));
            ConfigurableApplicationContext context = sa.run(args);
        }
}
