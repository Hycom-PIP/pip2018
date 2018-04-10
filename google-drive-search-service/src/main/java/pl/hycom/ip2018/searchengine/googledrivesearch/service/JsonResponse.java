package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class JsonResponse {

    @Autowired
    private RestTemplate restTemplate;

    public Object getUriAsObject(URI url) {
        return null;
    }
}
