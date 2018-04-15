package pl.hycom.ip2018.searchengine.wiki.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class intended for being processed by the Spring container
 * to generate bean definitions and service requests for those beans at runtime
 */
@Configuration
public class AppConfig {

    /**
     * Creates Spring bean, which allows us for synchronous client-side HTTP access
     * @return actual {@link RestTemplate} bean
     */
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

    /**
     * Creates Spring bean, which allows us serialize and deserialize Json to object and vice versa
     * @return actual {@link Gson} bean
     */
    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
