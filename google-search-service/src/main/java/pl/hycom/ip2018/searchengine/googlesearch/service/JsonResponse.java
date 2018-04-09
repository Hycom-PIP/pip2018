package pl.hycom.ip2018.searchengine.googlesearch.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    /**
     * Method that converts Map to json
     *
     * @param map input Map
     * @return String json result
     */
    public String getAsString(Map map) {
        return gson.toJson(map);
    }

    /**
     * Method that converts json to our Type
     *
     * @param json       input json which we want convert
     * @param resultType Type which we expected
     * @param <T>        generic Type
     * @return T generic Type which we expected
     */
    public <T> T getAsObject(String json, Type resultType) {
        return gson.fromJson(json, resultType);
    }

    /**
     * See javadoc for invoke
     *
     * @param url http address
     * @return Map mapped json
     */
    public Map getAsMap(URI url) {
        return invoke(url, Map.class);
    }

    /**
     * This method give us response from external API through HTTP protocol,
     * From default, java mapped response to Map type, and this type we use
     * to next operations
     *
     * @param url          http address
     * @param responseType Type which we expected
     * @param <T>          generic Type
     * @return T generic Type which we expected
     */
    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType);
        return exchange.getBody();
    }
}
