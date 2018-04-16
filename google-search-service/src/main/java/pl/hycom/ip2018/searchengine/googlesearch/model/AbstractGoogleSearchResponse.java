package pl.hycom.ip2018.searchengine.googlesearch.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Object representation of output
 */
public class AbstractGoogleSearchResponse {

    public static final Type TYPE = new TypeToken<AbstractGoogleSearchResponse>() {
    }.getType();

    @SerializedName("items")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
