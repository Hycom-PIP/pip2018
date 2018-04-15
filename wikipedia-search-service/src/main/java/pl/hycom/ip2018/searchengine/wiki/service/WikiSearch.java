package pl.hycom.ip2018.searchengine.wiki.service;
import pl.hycom.ip2018.searchengine.wiki.model.AbstractWikiSearchResponse;

/**
 * Interface specifying a usage of WikiSearch.
 */
public interface WikiSearch {
    /**
     * By submitting a query, we receive a ready answer in the format of our data model
     * @param query we are searching for
     * @return AbstractWikiSearchResponse object with mapped data from HTTP response
     */
    AbstractWikiSearchResponse getResponseByQuery(String query);
}
