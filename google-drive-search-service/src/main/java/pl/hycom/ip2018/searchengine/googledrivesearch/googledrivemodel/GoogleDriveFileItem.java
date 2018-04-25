package pl.hycom.ip2018.searchengine.googledrivesearch.googledrivemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleDriveFileItem {

    @JsonProperty("name")
    private String header;

    @JsonProperty("description")
    private String snippet;

    @JsonProperty("modifiedTime")
    private String timestamp;

    @JsonProperty("webViewLink")
    private String url;

    @JsonProperty("mimeType")
    private String mimeType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("iconLink")
    private String iconLink;
}
