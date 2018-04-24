package pl.hycom.ip2018.searchengine.googledrivesearch.model;

import com.google.gson.reflect.TypeToken;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import java.lang.reflect.Type;

public class GoogleDriveSearchResponse extends ProviderResponse{

    public static final Type TYPE = new TypeToken<GoogleDriveSearchResponse>() {}.getType();

    private int code;

    private String message;

    private String date;

    public static Type getTYPE() {
        return TYPE;
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
