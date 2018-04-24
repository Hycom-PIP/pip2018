package pl.hycom.ip2018.searchengine.wiki.model;

import com.google.common.reflect.TypeToken;
import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Type;
import java.util.List;

/**
 *  Class which represents output data our data model
 */
public class AbstractWikiSearchResponse {

    public static final Type TYPE = new TypeToken<AbstractWikiSearchResponse>() {}.getType();

    @SerializedName("query")
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
