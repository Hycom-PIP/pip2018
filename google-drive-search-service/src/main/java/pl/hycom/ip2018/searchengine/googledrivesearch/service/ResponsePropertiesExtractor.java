package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.services.drive.model.File;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponsePropertiesExtractor {

    @Value("${prop.googledrive.kind}")
    private String kind;

    @Value("${prop.googledrive.nextPageToken}")
    private String nextPageToken;

    @Value("${prop.googledrive.incompleteSearch}")
    private String incompleteSearch;

    @Value("${prop.googledrive.files}")
    private String files;

    @Value("${prop.googledrive.id}")
    private String id;

    @Value("${prop.googledrive.name}")
    private String name;

    @Value("${prop.googledrive.header}")
    private String header;

    @Value("${prop.googledrive.webViewLink}")
    private String webViewLink;

    @Value("${prop.googledrive.webContentLink}")
    private String webContentLink;

    @Value("${prop.googledrive.mimeType}")
    private String mimeType;

    @Value("${prop.googledrive.createdTime}")
    private String createdTime;

    @Value("${prop.googledrive.timestamp}")
    private String timestamp;

    @Value("${prop.googledrive.url}")
    private String url;

    @Value("${prop.googledrive.snippet}")
    private String snippet;

    @Value("${prop.googledrive.defaultUrl}")
    private String defaultUrl;

    public Map<String, List<Map<String, String>>> makeSimpleMapFromFileList(Drive service, FileList response) {

        Map<String, List<Map<String, String>>> extractedResult = new LinkedHashMap<>();
        List<File> filesList = response.getFiles();
        List<Map<String, String>> extractedFiles = new ArrayList<>();
        for (File file : filesList) {
            Map<String, String> singleItem = new LinkedHashMap<>();
            singleItem.put(id, file.getId());
            singleItem.put(name, file.getName());
            singleItem.put(header, file.getName());
            singleItem.put(mimeType, file.getMimeType());
            singleItem.put(createdTime, file.getCreatedTime().toString());
            singleItem.put(webViewLink, file.getWebViewLink());
            singleItem.put(webContentLink, file.getWebContentLink());
            singleItem.put(timestamp, file.getModifiedTime().toString());
            singleItem.put(url, file.getWebViewLink());
//            singleItem.put(snippet, createSnippet(service, file)); //TODO: zrobic snippet, poki co nie pobiera zawartosci pliku
            extractedFiles.add(singleItem);
        }
//        extractedResult.put(kind, kindSection);
//        extractedResult.put(nextPageToken, nextPageTokenSection);
//        extractedResult.put(incompleteSearch, incompleteSearchSection);
        extractedResult.put(files, extractedFiles);
        return extractedResult;
    }

    private String createSnippet(Drive service, File file) {
        String snippet;

        String googleDocs = "application/vnd.google-apps.document";
        String googleDriveFolder = "application/vnd.google-apps.folder";
        String plainText = "text/plain";
        String jpgImage = "image/jpeg";
        String pngImage = "image/png";

        String mimeType = file.getMimeType();

        if (mimeType.equals(googleDocs)) {
            snippet = createSnippetFromFileContent(service, file, googleDocs);
        } else if (mimeType.equals(googleDriveFolder)) {
            snippet = "<Plik jest katalogiem.>";
        } else if (mimeType.equals(plainText)) {
            snippet = createSnippetFromFileContent(service, file, plainText);
        } else if (mimeType.equals(jpgImage) || mimeType.equals(pngImage)) {
            snippet = "<Plik nie jest plikiem tekstowym.>";
        } else {
            snippet = "<Nie udało się odczytać zawartości pliku.>";
        }

        return snippet;
    }

    private String createSnippetFromFileContent(Drive service, File file, String mimeType) {
        String snippet;
        try {
                String url = file.getWebContentLink();
                HttpResponse response = service.getRequestFactory()
                        .buildGetRequest(new GenericUrl(url)).execute();
                InputStream stream = response.getContent();
                snippet = stream.toString();
//            OutputStream outputStream = new ByteArrayOutputStream();
//            service.files().export(file.getId(), mimeType).executeMediaAndDownloadTo(outputStream);
////            service.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
//            snippet = outputStream.toString();
        } catch (IOException e) {
            snippet = "<Nie udało się odczytać zawartości pliku.>";
            e.printStackTrace();
        }
        return snippet;
    }

    public Map<String, List<Map<String, String>>> makeSimpleMapFromResponse(Map response) throws ClassCastException{

        Map<String, List<Map<String, String>>> extractedResult = new LinkedHashMap<>();

        List<Map<String, String>> kindSection = (List<Map<String, String>>) response.get(kind);
        List<Map<String, String>> nextPageTokenSection = (List<Map<String, String>>) response.get(nextPageToken);
        List<Map<String, String>> incompleteSearchSection = (List<Map<String, String>>) response.get(incompleteSearch);

        List<Map<String, String>> extractedFiles = new ArrayList<>();
        List<Map> filesSection = (List<Map>) response.get(files);

        for (Map fileMap : filesSection) {
            Map<String, String> singleItem = new LinkedHashMap<>();
            singleItem.put(id, (String) fileMap.get(id));
            singleItem.put(name, (String) fileMap.get(name));
            singleItem.put(webViewLink, (String) fileMap.get(webViewLink));
            extractedFiles.add(singleItem);
        }
        extractedResult.put(kind, kindSection);
        extractedResult.put(nextPageToken, nextPageTokenSection);
        extractedResult.put(incompleteSearch, incompleteSearchSection);
        extractedResult.put(files, extractedFiles);
        return extractedResult;
    }
}
