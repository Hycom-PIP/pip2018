package pl.hycom.ip2018.searchengine.googledrivesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Google Drive Search Service main
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GoogleDriveSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleDriveSearchApplication.class, args);
    }
}
