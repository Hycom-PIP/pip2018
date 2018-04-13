package pl.hycom.ip2018.searchengine.aggregate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.aggregate.service.AggregateSearch;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class AggregateController {

    @Autowired
    AggregateSearch aggregateSearch;

    @RequestMapping(value = "/res/{query}", method = GET)
    public ProviderResponse getMessage(@PathVariable String query) throws Exception {
        return aggregateSearch.getResponse(query);
    }
}
