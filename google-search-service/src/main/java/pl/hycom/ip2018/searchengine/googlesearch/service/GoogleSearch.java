package pl.hycom.ip2018.searchengine.googlesearch.service;

import pl.hycom.ip2018.searchengine.googlesearch.model.AbstractGoogleSearchResponse;

public interface GoogleSearch {

    /**
     * Returns response wrapped in our type
     *
     * @param query search parameter from user
     * @return AbstractGoogleSearchResponse
     */
    AbstractGoogleSearchResponse getResponseFromGoogleByQuery(String query);
}
