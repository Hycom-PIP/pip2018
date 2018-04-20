package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;

public interface GoogleDriveAuth {

    Credential getCredential();
    Drive getDrive(Credential credential);
}
