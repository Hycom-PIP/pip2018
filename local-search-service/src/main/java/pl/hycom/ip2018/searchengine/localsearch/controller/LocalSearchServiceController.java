package pl.hycom.ip2018.searchengine.localsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.localsearch.model.AbstractLocalSearchResponse;
import pl.hycom.ip2018.searchengine.localsearch.service.DefaultLocalSearch;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller returns json objects
 */
@RestController
public class LocalSearchServiceController {

    @Autowired
    private DefaultLocalSearch defaultLocalSearch;

    /**
     * Endpoint from other services
     *
     * @param query we are searching for
     * @return object representation of response
     */
    @RequestMapping(value = "/res/{query}", method = GET)
    public ResponseEntity<AbstractLocalSearchResponse> getResponseFromLocal(@PathVariable String query) {
        AbstractLocalSearchResponse result = defaultLocalSearch.getResponseFromLocalByQuery(query);
        return result == null
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.ok(result);
    }
}
