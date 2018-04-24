package pl.hycom.ip2018.searchengine.googlesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main for Google Search Service
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GoogleSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleSearchApplication.class, args);
    }
}
