package pl.hycom.ip2018.searchengine.wiki.service;

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

/**
 * Strategy class that specifies a converter from HTTP to appropriate object we need
 */
@Service
public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;


    /**
     * Create a new Object of Map type with given URI
     * @param url the URL on which we make the request
     * @return representation of Json in the form of a Map type
     */
    public Map getAsMap(URI url) {
        return invoke(url, Map.class);
    }

    /**
     * This method serializes the specified Map object into its equivalent Json representation
     * @param map object which be serialized
     * @return map object Json representation
     */
    public String getAsString(Map map) {
        return gson.toJson(map);
    }

    /**
     * This method deserialize the specified Json into an object of the specified type
     * @param string Json file representation
     * @param resultType type of the specified object to deserialize
     * @param <T> the type of the desired object
     * @return an object of type T from the string. Returns {@code null} if {@code json} is {@code null}
     */
    public <T> T getAsObject(String string, Type resultType) {
        return gson.fromJson(string, resultType);
    }


    /**
     * Create a new Object of T type with given URI and Json MediaType
     * @param url the URL on which we make the request
     * @param responseType the type of the desired object
     * @param <T> the type of the desired object
     * @return  representation of Json in the form of a <T> type
     */
    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity
                        .get(url) // Create an HTTP GET builder with the given url
                        .accept(MediaType.APPLICATION_JSON) // Set the acceptable media types ("application/json")
                        .build(); // Builds the request entity with no body (body = null)
        ResponseEntity<T> exchange = restTemplate.exchange(request, responseType); //  ("status", "headers", "body" -> representation of Json in the form of a T type)
        return exchange.getBody(); // ("body" -> representation of Json in the form of a T type)
    }
}
