package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class DefaultGoogleDriveSearch implements GoogleDriveSearch {

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private JsonResponse jsonResponse;

    @Override
    public Object test(String query) {
        URI url = new UriTemplate(baseUrl).expand(query);
        return jsonResponse.getUriAsObject(url);
    }
}
