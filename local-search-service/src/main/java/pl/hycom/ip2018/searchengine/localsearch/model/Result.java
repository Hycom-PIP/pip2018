package pl.hycom.ip2018.searchengine.localsearch.model;

import lombok.Data;

/**
 * Part of {@link AbstractLocalSearchResponse} describes searching results
 */
@Data
public class Result {

    private static final String PROVIDER = "local";

    private String provider = PROVIDER;

    private String header;

    private String snippet;

    private String timestamp;

    private String url;
}
