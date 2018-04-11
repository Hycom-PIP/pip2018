package pl.hycom.ip2018.searchengine.googlesearch.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import pl.hycom.ip2018.searchengine.googlesearch.util.ISOStringDateTypeAdapter;

import java.util.Date;

public class Result {

    private String provider = "google";

    @SerializedName("title")
    private String header;

    @SerializedName("snippet")
    private String snippet;

    //@JsonAdapter(ISOStringDateTypeAdapter.class)
    @SerializedName("article:modified_time")
    private String timestamp;

    @SerializedName("link")
    private String url;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
