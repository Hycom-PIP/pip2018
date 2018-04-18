package pl.hycom.ip2018.searchengine.localsearch.service;

import pl.hycom.ip2018.searchengine.localsearch.model.LocalSearchResponse;

/**
 * Interface specify usage of local disk
 */
public interface LocalSearch {

    /**
     * Returns response wrapped in our type
     *
     * @param query we are searching for
     * @return object representation of response
     */
    LocalSearchResponse getResponseFromLocalByQuery(String query);
}
