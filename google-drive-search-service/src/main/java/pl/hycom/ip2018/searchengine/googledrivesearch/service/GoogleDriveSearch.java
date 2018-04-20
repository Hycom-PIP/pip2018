package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.AbstractGoogleDriveSearchResponse;

import java.util.List;

public interface GoogleDriveSearch {

    AbstractGoogleDriveSearchResponse getResponseFromGoogleDriveByQuery(String query);
    FileList listFiles(Drive service);
    List<File> getFiles(FileList fileList);
}
