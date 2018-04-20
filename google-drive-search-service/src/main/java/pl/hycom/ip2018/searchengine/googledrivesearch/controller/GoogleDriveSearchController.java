package pl.hycom.ip2018.searchengine.googledrivesearch.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveAuth;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveSearch;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class GoogleDriveSearchController {

    @Autowired
    private GoogleDriveSearch googleDriveSearch;
    @Autowired
    private GoogleDriveAuth googleDriveAuth;

    @RequestMapping(value = "/res/{query}", method = GET)
    public String getResponseFromGoogleDrive(@PathVariable String query) {
        Credential credential = googleDriveAuth.getCredential();
        Drive drive = googleDriveAuth.getDrive(credential);
        FileList fileList = googleDriveSearch.listFiles(drive);
        List<File> files = googleDriveSearch.getFiles(fileList);
//        return googleDriveSearch.getResponseFromGoogleDriveByQuery(query);
        return files.toString();
    }
}
