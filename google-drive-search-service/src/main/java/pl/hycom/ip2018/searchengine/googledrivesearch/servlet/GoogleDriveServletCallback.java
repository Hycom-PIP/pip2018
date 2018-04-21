package pl.hycom.ip2018.searchengine.googledrivesearch.servlet;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class GoogleDriveServletCallback extends AbstractAuthorizationCodeCallbackServlet {

    @Override
    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                ServletUtils.CLIENT_ID,
                ServletUtils.CLIENT_SECRET,
                Collections
                        .singleton(DriveScopes.DRIVE_APPS_READONLY))
                        .setDataStoreFactory(ServletUtils.DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
    }

    @Override
    protected String getRedirectUri(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        GenericUrl url = new GenericUrl(httpServletRequest.getRequestURL().toString());
        url.setRawPath("/oauth2callback");
        return url.build();
    }

    @Override
    protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        Long userId = (Long) httpServletRequest.getSession().getAttribute(WebKeys.USER_ID);
        return String.valueOf(userId);
    }
}
