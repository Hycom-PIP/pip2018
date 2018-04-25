package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;

public interface GoogleDriveAuth {

    Drive getAuthDriveService();
}
