package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriTemplate;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;

import java.net.URI;
import java.util.Date;
import java.util.Map;

@Service
public class DefaultGoogleDriveSearch implements GoogleDriveSearch {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGoogleDriveSearch.class);

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private JsonResponse jsonResponse;

    @Autowired
    private ResponsePropertiesExtractor responsePropertiesExtractor;

    @Override
    public AbstractGoogleDriveSearchResponse getResponseFromGoogleDriveFromDriveByQuery(Drive drive, String query) {

        logger.info("Requesting searching results for {}", query);
        AbstractGoogleDriveSearchResponse result;

        try {
            URI uri = new UriTemplate(baseUrl).expand(query);
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

//    public void listFiles() {
//        try {
//            Drive service = getDriveService();
//            FileList result = service.files().list()
//                    .setPageSize(10)
//                    .setFields("nextPageToken, files(id, name)")
//                    .execute();
//            List<File> files = result.getFiles();
//            if (files == null || files.size() == 0) {
//                System.out.println("No files found.");
//            } else {
//                System.out.println("Files:");
//                for (File file : files) {
//                    System.out.printf("%s (%s)\n", file.getName(), file.getId());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
