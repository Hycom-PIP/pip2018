package pl.hycom.ip2018.searchengine.googlesearch.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import pl.hycom.ip2018.searchengine.googlesearch.util.SecondsDateTypeAdapter;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class AbstractGoogleSearchResponse {

    public static final Type TYPE = new TypeToken<AbstractGoogleSearchResponse>() {
    }.getType();

    @SerializedName("items")
    private List<Result> results;

    private int code;

    private String message;

    @JsonAdapter(SecondsDateTypeAdapter.class)
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
