package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.auth.oauth2.Credential;

public interface GoogleDriveAuth {

    Credential authorize();
}
