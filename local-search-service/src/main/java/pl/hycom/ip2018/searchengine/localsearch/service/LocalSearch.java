package pl.hycom.ip2018.searchengine.localsearch.service;

import pl.hycom.ip2018.searchengine.localsearch.model.AbstractLocalSearchResponse;

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
    AbstractLocalSearchResponse getResponseFromLocalByQuery(String query);
}
