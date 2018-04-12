package pl.hycom.ip2018.searchengine.localsearch.model;

import java.util.Date;
import java.util.List;

/**
 * Object representation of output
 */
public class AbstractLocalSearchResponse {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public AbstractLocalSearchResponse setResults(List<Result> results) {
        this.results = results;
        return this;
    }
}
