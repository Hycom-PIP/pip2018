package pl.hycom.ip2018.searchengine.wiki.model;
import com.google.gson.annotations.SerializedName;


/**
 *  Class which represents single output data our data model
 */
public class Result {

    private String provider = "wikipedia";

    @SerializedName("title")
    private String header;

    @SerializedName("snippet")
    private String snippet;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("url")
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
