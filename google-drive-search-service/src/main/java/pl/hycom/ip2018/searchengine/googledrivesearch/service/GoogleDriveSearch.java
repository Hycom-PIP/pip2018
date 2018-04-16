package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;

public interface GoogleDriveSearch {

    AbstractGoogleDriveSearchResponse getResponseFromGoogleDriveByQuery(String query);
}
