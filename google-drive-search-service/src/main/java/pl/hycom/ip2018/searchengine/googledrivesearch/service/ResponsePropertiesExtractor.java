package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponsePropertiesExtractor {

    @Value("${prop.googledrive.query}")
    private String query;

    @Value("${prop.googledrive.header}")
    private String header;

    @Value("${prop.googledrive.snippet}")
    private String snippet;

    @Value("${prop.googledrive.timestamp}")
    private String timestamp;

    @Value("${prop.googledrive.url}")
    private String url;

    @Value("${prop.googledrive.defaultUrl}")
    private String defaultUrl;

    public Map makeSimpleMapFromResponse(Map response) throws ClassCastException{

        Map<String, List<Map<String, String>>> result = new LinkedHashMap<>();
        List<Map<String, String>> items = new ArrayList<>();
        List<Map> tempList = (List<Map>) response.get(query);

        for (Map tempMap : tempList) {
            items.add(extract(tempMap));
        }
        result.put(query, items);
        return result;
    }

    private Map<String, String> extract(Map<String, String> response) {

        Map<String, String> singleExtractedResponse = new LinkedHashMap<>();
        singleExtractedResponse.put(header, response.get(header));
        singleExtractedResponse.put(snippet, response.get(snippet));
        singleExtractedResponse.put(timestamp, response.get(timestamp));
        singleExtractedResponse.put(url, defaultUrl +  response.get(header)); //TODO: do sprawdzenia, raczej źle

        return singleExtractedResponse;
    }
}
