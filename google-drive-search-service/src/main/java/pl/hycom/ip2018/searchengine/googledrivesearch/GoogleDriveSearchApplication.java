package pl.hycom.ip2018.searchengine.googledrivesearch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.hycom.ip2018.searchengine.googledrivesearch.converter.GoogleDriveResponseConverter;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.DefaultGoogleDriveSearch;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveSearch;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.JsonResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.ResponsePropertiesExtractor;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class GoogleDriveSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleDriveSearchApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    @Bean
    public ResponsePropertiesExtractor responsePropertiesExtractor() {
        return new ResponsePropertiesExtractor();
    }

    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        return new SimpleClientHttpRequestFactory();
    }

    @Bean
    public GoogleDriveSearch googleDriveSearch() {
        return new DefaultGoogleDriveSearch();
    }

    @Bean
    public GoogleDriveResponseConverter googleDriveResponseConverter() {
        return new GoogleDriveResponseConverter();
    }
}
