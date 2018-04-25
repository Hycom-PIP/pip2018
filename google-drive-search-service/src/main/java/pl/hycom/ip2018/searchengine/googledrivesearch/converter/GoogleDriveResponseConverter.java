package pl.hycom.ip2018.searchengine.googledrivesearch.converter;

import org.springframework.core.convert.converter.Converter;
import pl.hycom.ip2018.searchengine.googledrivesearch.googledrivemodel.GoogleDriveResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.GoogleDriveSearchResponse;
import pl.hycom.ip2018.searchengine.googledrivesearch.model.Result;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoogleDriveResponseConverter implements Converter<GoogleDriveResponse, GoogleDriveSearchResponse> {

    @Override
    public GoogleDriveSearchResponse convert(GoogleDriveResponse googleDriveResponse) {
        GoogleDriveSearchResponse result = new GoogleDriveSearchResponse();
        List<SimpleResult> simpleItems = new ArrayList<>();

        Optional.ofNullable(googleDriveResponse)
                .map(GoogleDriveResponse::getResults)
                .ifPresent(googleDriveFileItems -> googleDriveFileItems
                    .forEach(googleDriveFileItem -> {
                        Result item = new Result();
                        item.setHeader(googleDriveFileItem.getHeader());
                        item.setSnippet(googleDriveFileItem.getSnippet());
                        item.setTimestamp(googleDriveFileItem.getTimestamp());
                        item.setUrl(googleDriveFileItem.getUrl());
                        simpleItems.add(item);
                    }));

        result.setResults(simpleItems);
        return result;
    }
}
