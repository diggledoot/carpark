package com.example.carpark.config;

import com.example.carpark.externalservice.sggov.SgGovService;
import com.example.carpark.externalservice.sggov.SgGovServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalServiceConfig {

    @Bean
    public SgGovService sgGovService(
            @Value("${sg.gov.baseUrl}") String baseUrl
    ) {
        return new SgGovServiceImpl(RestClient.create(), baseUrl);
    }
}
