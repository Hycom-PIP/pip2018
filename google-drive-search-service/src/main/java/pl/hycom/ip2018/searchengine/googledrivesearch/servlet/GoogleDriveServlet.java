package pl.hycom.ip2018.searchengine.googledrivesearch.servlet;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class GoogleDriveServlet extends AbstractAuthorizationCodeServlet {

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    static {
        try {
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    @Value("${rest.api.clientId}")
    private String clientId;

    @Value("${rest.api.clientSecret}")
    private String clientSecret;

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                clientId, clientSecret,
                Collections.singleton(DriveScopes.DRIVE_APPS_READONLY)).setDataStoreFactory(
                DATA_STORE_FACTORY).setAccessType("offline").build();
    }

    @Override
    protected String getRedirectUri(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(httpServletRequest.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    @Override
    protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        return null;
    }
}
