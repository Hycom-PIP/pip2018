package pl.hycom.ip2018.searchengine.googlesearch.model;

import lombok.Data;

/**
 * Part of {@link AbstractGoogleSearchResponse} describes searching results
 */
@Data
public class Result {

    private static final String PROVIDER = "google";

    private String provider = PROVIDER;

    private String header;

    private String snippet;

    private String timestamp;

    private String url;
}
