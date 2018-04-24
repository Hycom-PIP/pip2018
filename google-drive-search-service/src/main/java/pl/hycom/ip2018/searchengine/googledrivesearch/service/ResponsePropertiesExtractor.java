package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponsePropertiesExtractor {

    @Value("${prop.googledrive.files}")
    private String files;

    @Value("${prop.googledrive.header}")
    private String header;

    @Value("${prop.googledrive.url}")
    private String url;

    @Value("${prop.googledrive.snippet}")
    private String snippet;

    @Value("${prop.googledrive.timestamp}")
    private String timestamp;

    @Value("${prop.googledrive.webViewLink}")
    private String webViewLink;

    @Value("${prop.googledrive.webContentLink}")
    private String webContentLink;

    @Value("${prop.googledrive.mimeType}")
    private String mimeType;

    @Value("${prop.googledrive.createdTime}")
    private String createdTime;

    @Value("${prop.googledrive.description}")
    private String description;

    @Value("${prop.googledrive.iconLink}")
    private String iconLink;

    @Value("${prop.googledrive.modifiedTime}")
    private String modifiedTime;

    @Value("${prop.googledrive.size}")
    private String size;

    public Map<String, List<Map<String, String>>> makeSimpleMapFromFileList(Drive service, FileList response) {

        Map<String, List<Map<String, String>>> extractedResult = new LinkedHashMap<>();
        List<File> filesList = response.getFiles();
        List<Map<String, String>> extractedFiles = new ArrayList<>();
        for (File file : filesList) {
            Map<String, String> singleItem = new LinkedHashMap<>();
            singleItem.put(header, file.getName());
            singleItem.put(snippet, createSnippet(service, file)); //TODO: zrobic snippet, poki co nie pobiera zawartosci pliku
            singleItem.put(timestamp, file.getModifiedTime().toString());
            singleItem.put(url, file.getWebViewLink());
            extractedFiles.add(singleItem);
        }
        extractedResult.put(files, extractedFiles);
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
//                String url = file.getWebViewLink();
//                HttpResponse response = service
//                        .getRequestFactory()
//                        .buildGetRequest(new GenericUrl(url))
//                        .execute();
//                InputStream stream = response.getContent();
//                snippet = stream.toString();

            InputStream stream = service.files().get(file.getId()).executeMedia().getContent();
            snippet = stream.toString();
//            OutputStream outputStream = new ByteArrayOutputStream();
//            service.files().export(file.getId(), file.getMimeType()).executeMediaAndDownloadTo(outputStream);
////            service.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
//            snippet = outputStream.toString();
        } catch (IOException e) {
            snippet = "<ERROR: Nie udało się odczytać zawartości pliku.>";
            e.printStackTrace();
        }
        return snippet;
    }
}
