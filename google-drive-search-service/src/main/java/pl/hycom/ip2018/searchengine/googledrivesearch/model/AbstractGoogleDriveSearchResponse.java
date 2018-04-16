package pl.hycom.ip2018.searchengine.googledrivesearch.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AbstractGoogleDriveSearchResponse {

    public static final Type TYPE = new TypeToken<AbstractGoogleDriveSearchResponse>() {
    }.getType();

    @SerializedName("results")
    private List<Result> results;

    private int code;

    private String message;

    private String date;

    public static Type getTYPE() {
        return TYPE;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
