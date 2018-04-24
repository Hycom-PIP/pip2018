package pl.hycom.ip2018.searchengine.localsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main for Local Search Service
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LocalSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalSearchApplication.class, args);
    }
}
