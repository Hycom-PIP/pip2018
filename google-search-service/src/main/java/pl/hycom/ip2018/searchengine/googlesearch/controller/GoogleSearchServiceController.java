package pl.hycom.ip2018.searchengine.googlesearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.googlesearch.model.AbstractGoogleSearchResponse;
import pl.hycom.ip2018.searchengine.googlesearch.service.GoogleSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
    @RequestMapping(value = "/test1/{query}", method = GET)
    public AbstractGoogleSearchResponse test1(@PathVariable String query) {
        return googleSearch.test(query);
    }
}
