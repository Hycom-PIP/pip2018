package pl.hycom.ip2018.searchengine.googledrivesearch.googledrivemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GoogleDriveResponse {

    @JsonProperty("files")
    private List<GoogleDriveFileItem> results;

    @JsonProperty("kind")
    private String kind;

    @JsonProperty("nextPageToken")
    private String nextPageToken;

    @JsonProperty("incompleteSearch")
    private String incompleteSearch;
}
