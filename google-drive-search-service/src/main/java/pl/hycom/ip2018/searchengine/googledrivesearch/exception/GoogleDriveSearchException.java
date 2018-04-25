package pl.hycom.ip2018.searchengine.googledrivesearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class GoogleDriveSearchException extends Exception {

    public GoogleDriveSearchException() {
    }

    public GoogleDriveSearchException(String message) {
        super("GoogleDriveSearchException: " + message);
    }
}
