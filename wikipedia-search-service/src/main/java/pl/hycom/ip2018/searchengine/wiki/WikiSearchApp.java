package pl.hycom.ip2018.searchengine.wiki;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * Wikipedia service main
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WikiSearchApp {

	public static void main(String[] args) {
		SpringApplication.run(WikiSearchApp.class, args);
	}

}
