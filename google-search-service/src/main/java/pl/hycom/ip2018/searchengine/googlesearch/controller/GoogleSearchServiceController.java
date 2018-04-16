package pl.hycom.ip2018.searchengine.googlesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googlesearch.model.AbstractGoogleSearchResponse;
import pl.hycom.ip2018.searchengine.googlesearch.service.GoogleSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller returns json objects
 */
@RestController
public class GoogleSearchServiceController {

    @Autowired
    private GoogleSearch googleSearch;

    /**
     * Endpoint for aggregate service
     *
     * @param query phrase which we search
     * @return AbstractGoogleSearchResponse
     */
    @RequestMapping(value = "/res/{query}", method = GET)
    public ResponseEntity<AbstractGoogleSearchResponse> getResponseFromGoogle(@PathVariable String query) {
        AbstractGoogleSearchResponse result = googleSearch.getResponseFromGoogleByQuery(query);
        return result == null
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.ok(result);
    }
}
