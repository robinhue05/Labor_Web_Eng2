package de.dhbw.skywatch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

// One shared HTTP client for the NASA API (values come from application.properties)
@Configuration
public class NasaApiConfig {

    @Value("${nasa.api.base-url}")
    private String baseUrl;

    @Value("${nasa.api.connect-timeout-ms}")
    private int connectTimeoutMs;

    @Value("${nasa.api.read-timeout-ms}")
    private int readTimeoutMs;

    @Bean
    public RestClient nasaRestClient() {
        // connect = time to open the connection, read = time to wait for the answer
        // without these a slow NASA would block our threads forever
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        factory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
