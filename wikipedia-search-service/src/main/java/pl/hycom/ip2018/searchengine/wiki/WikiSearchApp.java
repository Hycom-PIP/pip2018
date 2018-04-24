package pl.hycom.ip2018.searchengine.wiki;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * Wikipedia service main
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WikiSearchApp {

	public static void main(String[] args) {
		SpringApplication.run(WikiSearchApp.class, args);
	}

}
