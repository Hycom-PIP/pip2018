package pl.hycom.ip2018.searchengine.providercontract;

import lombok.Data;

import java.util.List;

@Data
public class ProviderResponse {

    protected List<SimpleResult> results;
}
