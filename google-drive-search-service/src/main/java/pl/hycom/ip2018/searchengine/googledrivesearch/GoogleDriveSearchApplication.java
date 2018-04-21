package pl.hycom.ip2018.searchengine.googledrivesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.JsonResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.ResponsePropertiesExtractor;
import pl.hycom.ip2018.searchengine.googledrivesearch.servlet.GoogleDriveServlet;
import pl.hycom.ip2018.searchengine.googledrivesearch.servlet.GoogleDriveServletCallback;

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

    @Bean
    public JsonResponse jsonResponse() {
        return new JsonResponse();
    }

    @Bean
    public ResponsePropertiesExtractor responsePropertiesExtractor() {
        return new ResponsePropertiesExtractor();
    }

    @Bean
    public ServletRegistrationBean googleDriveServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new GoogleDriveServlet(), "/res/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean ServletRegistrationBean googleDriveServletCallback() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new GoogleDriveServletCallback(), "/res/*");
        bean.setLoadOnStartup(2);
        return bean;
    }

    private SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        return new SimpleClientHttpRequestFactory();
    }
}
