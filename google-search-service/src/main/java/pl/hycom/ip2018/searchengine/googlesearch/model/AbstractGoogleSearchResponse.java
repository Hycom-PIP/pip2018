package pl.hycom.ip2018.searchengine.googlesearch.model;

import lombok.Data;

import java.util.List;

/**
 * Object representation of output
 */
@Data
public class AbstractGoogleSearchResponse {

    private List<Result> results;
}
