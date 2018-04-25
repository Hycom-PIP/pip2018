package pl.hycom.ip2018.searchengine.googledrivesearch.controller;

import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.GoogleDriveSearchResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveAuth;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GoogleDriveSearchController {

    @Autowired
    private GoogleDriveSearch googleDriveSearch;
    @Autowired
    private GoogleDriveAuth googleDriveAuth;

    @RequestMapping(value = "/res/{query}", method = GET)
    public GoogleDriveSearchResponse getResponseFromGoogleDrive(@PathVariable String query) {
        Drive drive = googleDriveAuth.getAuthDriveService();
        return googleDriveSearch.getResponseFromGoogleDriveByQuery(drive, query);
    }
}
