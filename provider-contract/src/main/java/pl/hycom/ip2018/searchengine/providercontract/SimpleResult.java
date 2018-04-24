package pl.hycom.ip2018.searchengine.providercontract;

import lombok.Data;

@Data
public class SimpleResult {

    protected String provider;

    protected String header;

    protected String snippet;

    protected String timestamp;

    protected String url;
}
