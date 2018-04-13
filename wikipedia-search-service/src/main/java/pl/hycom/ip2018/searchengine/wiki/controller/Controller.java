package pl.hycom.ip2018.searchengine.wiki.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;
import pl.hycom.ip2018.searchengine.wiki.exception.WikipediaException;
import pl.hycom.ip2018.searchengine.wiki.service.WikiSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Class marked as controller, where every method returns a domain object instead of a view
 */

@Api
@RestController
public class Controller {

    @Autowired
    private WikiSearch wikiSearch;

    /**
     * Endpoint for wikipedia aggregate service
     * @param query phrase we are searching for
     * @return domain object WikiSearchResponse
     */
    @RequestMapping(value = "/res/{query}", method = GET)
    public ProviderResponse getResponseFromWiki(@PathVariable String query) throws WikipediaException {
        return wikiSearch.getResponse(query);
    }
}
