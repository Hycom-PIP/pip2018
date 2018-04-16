package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import org.springframework.beans.factory.annotation.Value;

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

    @Value("${prop.googledrive.webViewLink}")
    private String webViewLink;

    @Value("${prop.googledrive.url}")
    private String url;

    @Value("${prop.googledrive.defaultUrl}")
    private String defaultUrl;

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
