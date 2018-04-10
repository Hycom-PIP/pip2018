package pl.hycom.ip2018.searchengine.googlesearch;

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
import pl.hycom.ip2018.searchengine.googlesearch.service.JsonResponse;
import pl.hycom.ip2018.searchengine.googlesearch.service.ResponsePropertiesExtractor;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class GoogleSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogleSearchApplication.class, args);
    }

    /**
     * Rest Template Bean for receiving data from API
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(formHttpMessageConverter);
        converters.add(stringHttpMessageConverter);
        converters.add(jacksonConverter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        return new SimpleClientHttpRequestFactory();
    }

    /**
     * Gson Bean for operations on json
     *
     * @return Gson
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    /**
     * Json Response Bean for operations on response from API
     *
     * @return JsonResponse
     */
    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    /**
     * Response Properties Extractor for changing huge google response
     * to small Map which we need
     *
     * @return ResponsePropertiesExtractor
     */
    @Bean
    public ResponsePropertiesExtractor responsePropertiesExtractor() {
        return new ResponsePropertiesExtractor();
    }
}
