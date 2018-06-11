package pl.hycom.ip2018.searchengine.ui.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.Accounts;
import com.google.api.services.analytics.model.Profiles;
import com.google.api.services.analytics.model.Webproperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
public class GoogleAnalyticsAuth {
    private static final String APPLICATION_NAME = "Search engine";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String KEY_FILE_LOCATION = "/service_account_secret.json";

    /**
     * Initializes an Analytics service object.
     * @return An authorized Analytics service object.
     * @throws IOException in case of error in Analytics or invalid path to key file
     * @throws GeneralSecurityException in case of error in Analytics
     */
    public static Analytics initializeAnalytics() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(GoogleAnalyticsAuth.class.getResourceAsStream(KEY_FILE_LOCATION))
                .createScoped(AnalyticsScopes.all());

        return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }


    /**
     * @param analytics authorized service object
     * @return profileId
     * @throws IOException in case of error in Analytics
     */
    public static String getFirstProfileId(Analytics analytics) throws IOException {
        String profileId = null;
        Accounts accounts = analytics.management().accounts().list().execute();
        if (accounts.getItems().isEmpty()) {
            log.error("No accounts found");
        } else {
            String firstAccountId = accounts.getItems().get(0).getId();
            Webproperties properties = analytics.management().webproperties()
                    .list(firstAccountId).execute();
            if (properties.getItems().isEmpty()) {
                log.error("No Webproperties found");
            } else {
                String firstWebpropertyId = properties.getItems().get(0).getId();
                Profiles profiles = analytics.management().profiles()
                        .list(firstAccountId, firstWebpropertyId).execute();
                if (profiles.getItems().isEmpty()) {
                    log.error("No views (profiles) found");
                } else {
                    profileId = profiles.getItems().get(0).getId();
                }
            }
        }
        return profileId;
    }
}
