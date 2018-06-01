package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static pl.hycom.ip2018.searchengine.googledrivesearch.model.Result.PROVIDER;
import static pl.hycom.ip2018.searchengine.googledrivesearch.model.Result.PROVIDER_KEY;

/**
 * Class created to extract properties from response obtained from Google Drive API.
 */
@Slf4j
public class ResponsePropertiesExtractor {

    @Autowired
    private Environment environment;

    /**
     * Makes map response from FileList obtained from Drive API
     * @param service Drive service that manages files in Drive
     * @param response object of model that specifies how to parse into the JSON when working with Drive API
     * @return map containing field header and its content
     */
    public Map<String, List<Map<String, Object>>> makeSimpleMapFromFileList(Drive service, FileList response) {
        Map<String, List<Map<String, Object>>> extractedResult = new LinkedHashMap<>();
        List<File> filesList = response.getFiles();
        List<Map<String, Object>> extractedFiles = new ArrayList<>();
        for (File file : filesList) {
            Map<String, Object> singleItem = new LinkedHashMap<>();
            Map<String, String> additionalData = new LinkedHashMap<>();

            singleItem.put(PROVIDER_KEY, PROVIDER);
            singleItem.put(environment.getProperty("prop.googledrive.header"), file.getName());
            singleItem.put(environment.getProperty("prop.googledrive.url"), file.getWebViewLink());
            additionalData.put(environment.getProperty("prop.googledrive.snippet"), createSnippet(service, file));
            additionalData.put(environment.getProperty("prop.googledrive.mimeType"), file.getMimeType());
            additionalData.put(environment.getProperty("prop.googledrive.description"), file.getDescription());
            additionalData.put(environment.getProperty("prop.googledrive.webContentLink"), file.getWebContentLink());
            additionalData.put(environment.getProperty("prop.googledrive.iconLink"), file.getIconLink());
            additionalData.put(environment.getProperty("prop.googledrive.createdTime"), file.getCreatedTime().toString());
            additionalData.put(environment.getProperty("prop.googledrive.modifiedTime"), file.getModifiedTime().toString());
            additionalData.put(environment.getProperty("prop.googledrive.size"), String.valueOf(file.size()));
            singleItem.put("additionalData", additionalData);
            extractedFiles.add(singleItem);
        }
        extractedResult.put(environment.getProperty("prop.googledrive.results"), extractedFiles);
        return extractedResult;
    }

    private String createSnippet(Drive service, File file) {
        String snippet;

        String googleDocs = "application/vnd.google-apps.document";
        String googleDriveFolder = "application/vnd.google-apps.folder";
        String plainText = "text/plain";

        String mimeType = file.getMimeType();

        if (mimeType.equals(googleDocs)) {
            snippet = "<Nie udało się odczytać zawartości pliku.>";
        } else if (mimeType.equals(googleDriveFolder)) {
            snippet = "<Plik jest katalogiem.>";
        } else if (mimeType.equals(plainText)) {
            snippet = createSnippetFromFileContent(service, file);
        } else {
            snippet = "<Plik nie jest plikiem tekstowym.>";
        }

        return snippet;
    }

    private String createSnippetFromFileContent(Drive service, File file) {
        String snippet;
        try {
            InputStream stream = service.files().get(file.getId()).executeMedia().getContent();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            snippet = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            log.error("Error while retrieving snippet from file {} from service {}", file, service, e);
            snippet = "<Nie udało się odczytać zawartości pliku.>";
            e.printStackTrace();
        }
        return snippet;
    }
}
