package pl.hycom.ip2018.searchengine.googlesearch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriTemplate;
import pl.hycom.ip2018.searchengine.googlesearch.model.AbstractGoogleSearchResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link GoogleSearch} to get data by query
 */
@Service
public class DefaultGoogleSearch implements GoogleSearch {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGoogleSearch.class);

    @Value("${rest.api.apiKey}")
    private String apiKey;

    @Value("${rest.api.engineId}")
    private String engineId;

    @Value("${rest.api.language}")
    private String language;

    @Value("${rest.api.resultsMultiplier}")
    private String resultsMultiplier;

    @Value("${prop.google.itemsKey}")
    private String itemsKey;

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private JsonResponse jsonResponse;

    @Autowired
    private ResponsePropertiesExtractor responsePropertiesExtractor;

    /**
     * Returns response wrapped in our type
     *
     * @param query search parameter from user
     * @return AbstractGoogleSearchResponse
     */
    @Override
    public AbstractGoogleSearchResponse getResponseFromGoogleByQuery(String query) {

        logger.info("Requesting searching results for {}", query);
        AbstractGoogleSearchResponse result = null;

        try {
            List<Map<String, List<Map<String, String>>>> partialMaps = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(resultsMultiplier); i++) {
                URI url = new UriTemplate(baseUrl).expand(apiKey, engineId, language, query, i * 10 + 1);
                Map response = jsonResponse.getAsMap(url);
                partialMaps.add(responsePropertiesExtractor.makeSimpleMapFromResponse(response));
            }
            String fromSimpleMapToJson = jsonResponse.getAsString(joinMaps(partialMaps));
            result = jsonResponse.getAsObject(fromSimpleMapToJson, AbstractGoogleSearchResponse.TYPE);
        } catch (ResourceAccessException
                | HttpClientErrorException e) {
            logger.error("Searching results for {} are not available from Google", query);
        } catch (ClassCastException e) {
            logger.error("Google changed their API");
        }

        return result;
    }

    private Map joinMaps(List<Map<String, List<Map<String, String>>>> partialMaps) {
        List<Map<String, String>> subResult = new ArrayList<>();
        partialMaps.forEach(oneRes -> subResult.addAll(oneRes.get(itemsKey)));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put(itemsKey, subResult);
        return result;
    }
}
