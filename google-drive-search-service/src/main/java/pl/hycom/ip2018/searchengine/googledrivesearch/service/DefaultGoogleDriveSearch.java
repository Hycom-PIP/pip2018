package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class DefaultGoogleDriveSearch implements GoogleDriveSearch {

    @Value("${rest.api.apiKey}")
    private String apiKey;

    @Value("${rest.api.engineId}")
    private String engineId;

    @Value("${rest.api.language}")
    private String language;

    @Value("${rest.api.start}")
    private String start;

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object test(String query) {
        URI url = new UriTemplate(baseUrl).expand(apiKey, engineId, language, query, start);
        return invoke(url, Object.class);
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }
}
