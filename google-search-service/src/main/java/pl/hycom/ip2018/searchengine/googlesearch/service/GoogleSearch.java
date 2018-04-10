package pl.hycom.ip2018.searchengine.googlesearch.service;

import pl.hycom.ip2018.searchengine.googlesearch.model.AbstractGoogleSearchResponse;

public interface GoogleSearch {

    AbstractGoogleSearchResponse test(String query);
}
