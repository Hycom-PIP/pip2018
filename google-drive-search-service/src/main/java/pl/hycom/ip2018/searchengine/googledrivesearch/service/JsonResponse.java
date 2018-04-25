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
    private Gson gson;

    public String getAsString(Map map) {
        return gson.toJson(map);
    }

    public <T> T getAsObject(String json, Type resultType) {
        return gson.fromJson(json, resultType);
    }
}
