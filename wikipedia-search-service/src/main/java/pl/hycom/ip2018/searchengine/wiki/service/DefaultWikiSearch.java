package pl.hycom.ip2018.searchengine.wiki.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriTemplate;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import pl.hycom.ip2018.searchengine.wiki.model.AbstractWikiSearchResponse;



/**
 * Implementation of {@link WikiSearch} to get appropriate data type from i.e String query
 */
@Service
public class DefaultWikiSearch implements WikiSearch {

    private static final Logger logger = LoggerFactory.getLogger(DefaultWikiSearch.class);

    @Value("${rest.api.baseUrl}")
    private String baseUrl;

    @Value("${rest.api.srlimit}")
    private String srlimit;

    @Value("${rest.api.srinterwiki}")
    private String srinterwiki;

    @Autowired
    private ResponsePropertiesExtractor responsePropertiesExtractor;

    @Autowired
    private JsonResponse response;

    /**
     * By submitting a query, we receive a ready answer in the {@link AbstractWikiSearchResponse} data model
     * @param query we are searching for
     * @return AbstractWikiSearchResponse object with mapped data from HTTP response
     */
    @SuppressWarnings("unchecked")
    @Override
    public AbstractWikiSearchResponse getResponseByQuery(String query) {

        logger.info("Requesting searching results for {}", query);
        AbstractWikiSearchResponse result;

        try {
            URI url = new UriTemplate(baseUrl).expand(srlimit, srinterwiki, query);
            // map url tp map which let us to extract appropriate data
            Map responseAsMap =  response.getAsMap(url);
            // extract data
            Map simpleMap = responsePropertiesExtractor.makeSimpleMapFromResponse(responseAsMap);
            // get extracted data as String
            String fromSimpleMapToJson = response.getAsString(simpleMap);
            // map correct String with data to our Object
            result = response.getAsObject(fromSimpleMapToJson, AbstractWikiSearchResponse.TYPE);
            result.setCode(200);
            result.setMessage("OK");
            result.setDate(new Date().toString());
        } catch (ResourceAccessException | HttpClientErrorException e) {
            logger.error("Searching results for {} are not available from Google", query);
            result = new AbstractWikiSearchResponse();
            result.setCode(500);
            result.setMessage("Internal Server Error");
            result.setDate(new Date().toString());
        } catch (ClassCastException e) {
            logger.error("Wikipedia changed their API");
            result = new AbstractWikiSearchResponse();
            result.setCode(500);
            result.setMessage("Wikipedia changed their API");
            result.setDate(new Date().toString());
        }

        return result;
    }
}
