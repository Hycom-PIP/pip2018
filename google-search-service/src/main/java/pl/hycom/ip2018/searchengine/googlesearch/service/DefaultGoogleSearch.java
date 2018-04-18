package pl.hycom.ip2018.searchengine.googlesearch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriTemplate;
import pl.hycom.ip2018.searchengine.googlesearch.converter.GoogleResponseConverter;
import pl.hycom.ip2018.searchengine.googlesearch.model.GoogleSearchResponse;
import pl.hycom.ip2018.searchengine.googlesearch.googlemodel.GoogleResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link GoogleSearch} to get data by query
 */
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

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private JsonResponse jsonResponse;

    @Autowired
    private GoogleResponseConverter googleResponseConverter;

    /**
     * Returns response wrapped in our type
     *
     * @param query search parameter from user
     * @return GoogleSearchResponse
     */
    @Override
    public GoogleSearchResponse getResponseFromGoogleByQuery(String query) {

        logger.info("Requesting searching results for {}", query);
        try {
            List<GoogleResponse> partialList = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(resultsMultiplier); i++) {
                URI url = new UriTemplate(baseUrl).expand(apiKey, engineId, language, query, i * 10 + 1);
                GoogleResponse response = jsonResponse.invoke(url, GoogleResponse.class);
                partialList.add(response);
            }
            return join(partialList);
        } catch (Exception e) {
            logger.error("Searching results for {} are not available from Google", query);
            return null;
        }
    }

    private GoogleSearchResponse join(List<GoogleResponse> partialList) {
        GoogleSearchResponse result = new GoogleSearchResponse();
        result.setResults(new ArrayList<>());
        partialList.forEach(partial ->
                result.getResults().addAll(googleResponseConverter.convert(partial).getResults())
        );
        return result;
    }
}
