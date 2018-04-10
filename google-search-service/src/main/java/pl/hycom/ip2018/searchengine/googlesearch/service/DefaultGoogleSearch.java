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
import java.util.Date;
import java.util.Map;

@Service
public class DefaultGoogleSearch implements GoogleSearch {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGoogleSearch.class);

    @Value("${rest.api.apiKey}")
    private String apiKey;

    @Value("${rest.api.engineId}")
    private String engineId;

    @Value("${rest.api.language}")
    private String language;

    @Value("${rest.api.start}")
    private String start;

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
    public AbstractGoogleSearchResponse test(String query) {

        logger.info("Requesting searching results for {}", query);
        AbstractGoogleSearchResponse result;

        try {
            URI url = new UriTemplate(baseUrl).expand(apiKey, engineId, language, query, start);
            Map response = jsonResponse.getAsMap(url);
            Map simpleMap = responsePropertiesExtractor.makeSimpleMapFromResponse(response);
            String fromSimpleMapToJson = jsonResponse.getAsString(simpleMap);
            result = jsonResponse.getAsObject(fromSimpleMapToJson, AbstractGoogleSearchResponse.TYPE);
            result.setCode(200);
            result.setMessage("OK");
            result.setDate(new Date());
        } catch (ResourceAccessException
                | HttpClientErrorException e) {
            logger.error("Searching results for {} are not available from Google", query);
            result = new AbstractGoogleSearchResponse();
            result.setCode(500);
            result.setMessage("Internal Server Error");
            result.setDate(new Date());
        } catch (ClassCastException e) {
            logger.error("Google changed their API");
            result = new AbstractGoogleSearchResponse();
            result.setCode(500);
            result.setMessage("Google changed their API");
            result.setDate(new Date());
        }

        return result;
    }
}
