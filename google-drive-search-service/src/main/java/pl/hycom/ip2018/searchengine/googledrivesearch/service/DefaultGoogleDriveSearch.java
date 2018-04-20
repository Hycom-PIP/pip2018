package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriTemplate;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DefaultGoogleDriveSearch implements GoogleDriveSearch {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGoogleDriveSearch.class);

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Value("${rest.api.size}")
    private String size;

    @Value("${rest.api.pageToken}")
    private String pageToken;

    @Value("${rest.api.fields}")
    private String fields;

    @Value("${rest.api.authUrl}")
    private String authUrl;

    @Value("${rest.api.clientId}")
    private String clientId;

    @Autowired
    private JsonResponse jsonResponse;

    @Autowired
    private ResponsePropertiesExtractor responsePropertiesExtractor;

    @Override
    public AbstractGoogleDriveSearchResponse getResponseFromGoogleDriveByQuery(String query) {

        logger.info("Requesting searching results for {}", query);
        AbstractGoogleDriveSearchResponse result;

        try {
            URI uri = new UriTemplate(baseUrl).expand(size, query, fields);
            Map response = jsonResponse.getAsMap(uri);
            Map simpleMap = responsePropertiesExtractor.makeSimpleMapFromResponse(response);
            String fromSimpleMapToJson = jsonResponse.getAsString(simpleMap);
            result = jsonResponse.getAsObject(fromSimpleMapToJson, AbstractGoogleDriveSearchResponse.TYPE);
            result.setCode(200);
            result.setMessage("OK");
            result.setDate(new Date().toString());
        } catch (ResourceAccessException | HttpClientErrorException e) {
            logger.error("Searching results for {} are not available from Google Drive", query);
            result = new AbstractGoogleDriveSearchResponse();
            result.setCode(500);
            result.setMessage("Internal Server Error");
            result.setDate(new Date().toString());
        } catch (ClassCastException e) {
            logger.error("Google Drive changed their API");
            result = new AbstractGoogleDriveSearchResponse();
            result.setCode(500);
            result.setMessage("Wikipedia changed their API");
            result.setDate(new Date().toString());
        }

        return result;
    }

    @Override
    public FileList listFiles(Drive service) {
        FileList result = null;
        try {
            result = service.files().list()
                    .setPageSize(Integer.getInteger(size))
                    .setFields(fields)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<File> getFiles(FileList fileList) {
        return fileList.getFiles();
    }
}
