package pl.hycom.ip2018.searchengine.wiki.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ResponsePropertiesExtractor {

    @Value("${prop.wikipedia.query}")
    private String query;

    @Value("${prop.wikipedia.search}")
    private String search;

    @Value("${prop.wikipedia.interwikisearch}")
    private String interwikisearch;

    @Value("${prop.wikipedia.title}")
    private String title;

    @Value("${prop.wikipedia.snippet}")
    private String snippet;

    @Value("${prop.wikipedia.timestamp}")
    private String timestamp;

    @Value("${rest.api.srinterwiki}")
    private String srinterwiki;

    @Value("${prop.wikipedia.defaultUrl}")
    private String defaultUrl;

    @Value("${prop.wikipedia.url}")
    private String url;


    /**
     * Method define the manner of conversation between external HTTPresponse and our data model
     * @param HTTPresponse fully response from wikipedia API which have to be extracted
     * @return extracted data from wiki API via our model
     * @throws ClassCastException
     */
    @SuppressWarnings("unchecked")
    public Map<String, List<Map<String, String>>> makeSimpleMapFromResponse(Map<String, List<Map<String, String>>> HTTPresponse) throws ClassCastException {

        Map extractedQuery = new LinkedHashMap();
        List<Map> extractedValue = new ArrayList<>();

        // get the right section from the map ( query contains needed data )
        Map querySection = (Map) HTTPresponse.get(query);

        // iterate each search section in query section and extract appropriate data
        for(Map map : (List<Map>) querySection.get(search)) {
            extractedValue.add(extract(map));
        }

        if(Boolean.valueOf(srinterwiki)) {

            // interWiki has a bit different structure than plWiki
            Map<String, Object> interWikiSearchResponse = (Map) querySection.get(interwikisearch);

            // we are using this structure because weird keys are given
            for(Map.Entry<String, Object> entry : interWikiSearchResponse.entrySet())
            {
                // iterate each object and extract appropriate data
                for(Map map : (List<Map>) entry.getValue()) {
                    extractedValue.add(extract(map));
                }
            }
        }

        extractedQuery.put(query, extractedValue);
        return extractedQuery;
    }

    /**
     * Method define the manner of conversation between single external HTTPresponse and our data model
     * @param HTTPsingleResponse single piece of response from wikipedia API which has to be extracted
     * @return single extracted data from wiki API
     */
    private Map extract(Map<String, String> HTTPsingleResponse) {

        Map<String, String> singleExtractedResponse = new LinkedHashMap<>();
        singleExtractedResponse.put(title, HTTPsingleResponse.get(title));
        singleExtractedResponse.put(snippet, HTTPsingleResponse.get(snippet));
        singleExtractedResponse.put(timestamp, HTTPsingleResponse.get(timestamp));
        singleExtractedResponse.put(url,defaultUrl +  HTTPsingleResponse.get(title));

        return singleExtractedResponse;
    }
}
