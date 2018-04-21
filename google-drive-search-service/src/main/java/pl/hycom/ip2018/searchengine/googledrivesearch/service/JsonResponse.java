package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

@Service
public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    public String getAsString(Map map) {
        return gson.toJson(map);
    }

    public <T> T getAsObject(String json, Type resultType) {
        return gson.fromJson(json, resultType);
    }

    public Map getAsMap(URI url) {
        return invoke(url, Map.class);
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
