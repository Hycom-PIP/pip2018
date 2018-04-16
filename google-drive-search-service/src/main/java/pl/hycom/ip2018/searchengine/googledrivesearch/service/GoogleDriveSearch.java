package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;

public interface GoogleDriveSearch {

    AbstractGoogleDriveSearchResponse getResponseFromGoogleDriveFromDriveByQuery(Drive drive, String query);
}
