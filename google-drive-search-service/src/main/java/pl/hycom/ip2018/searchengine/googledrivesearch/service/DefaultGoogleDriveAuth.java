package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;
import pl.hycom.ip2018.searchengine.googledrivesearch.GoogleDriveSearchApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class DefaultGoogleDriveAuth implements GoogleDriveAuth{
        private static final String APPLICATION_NAME = "GoogleDriveSearchEngine\t";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static NetHttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final Set<String> SCOPES =
            DriveScopes.all()
            ;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public Credential getCredential() {
        Credential credential = null;
        GoogleCredential googleCredential = null;
        try {
//            InputStream in =
//                    GoogleDriveSearchApplication.class.getResourceAsStream("/client_secret.json");
//            GoogleClientSecrets clientSecrets =
//                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//            // Build flow and trigger user authorization request.
//            GoogleAuthorizationCodeFlow flow  = new GoogleAuthorizationCodeFlow.Builder(
//                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                    .setDataStoreFactory(DATA_STORE_FACTORY)
//                    .setAccessType("offline")
//                    .build();
//
//            credential = new AuthorizationCodeInstalledApp(
//                    flow, new LocalServerReceiver()).authorize("user");

            InputStream in = GoogleDriveSearchApplication.class.getResourceAsStream("/service_account_secret.json");
            googleCredential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleCredential;
    }

    @Override
    public Drive getDrive(Credential credential) {
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}