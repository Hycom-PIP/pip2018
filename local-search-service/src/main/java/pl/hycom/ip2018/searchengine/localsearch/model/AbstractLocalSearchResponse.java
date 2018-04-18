package pl.hycom.ip2018.searchengine.localsearch.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Object representation of output
 */
@Data
public class AbstractLocalSearchResponse {

    private List<Result> results;
}
