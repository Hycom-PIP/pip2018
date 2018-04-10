package pl.hycom.ip2018.searchengine.googledrivesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveAuth;
import pl.hycom.ip2018.searchengine.googledrivesearch.service.GoogleDriveSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {

    @Autowired
    private GoogleDriveAuth googleDriveAuth;
    @Autowired
    private GoogleDriveSearch googleDriveSearch;

    @RequestMapping(value = "/testGoogleDriveSearch/{query}", method = GET)
    public Object testGoogleDriveSearch(@PathVariable String query) {
        return googleDriveSearch.test(query);
    }
}
