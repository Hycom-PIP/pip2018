package pl.hycom.ip2018.searchengine.aggregate.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import pl.hycom.ip2018.searchengine.aggregate.service.AggregateSearch;
import pl.hycom.ip2018.searchengine.aggregate.service.CacheService;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

@RestController
@Api
@Slf4j
public class AggregateController {

    @Autowired
    private AggregateSearch aggregateSearch;

    @Autowired
    private CacheService cacheService;

    @HystrixCommand(fallbackMethod = "getMessageFallBack", commandKey = "Aggregate-Search-Service", groupKey = "GetMessage")
    @RequestMapping(value = "/res", method = GET)
    public ProviderResponse getMessage(@RequestParam("query") String query,
            @RequestParam(required = false) List<String> provider,
            @RequestHeader("x-user-id") String userId) {

        cacheService.addQueryToCache(userId, query);

        return aggregateSearch.getResponse(query, provider);
    }

    public ProviderResponse getMessageFallBack(String query, List<String> provider, HttpServletResponse response, HttpServletRequest req, Throwable e) {
        if (log.isWarnEnabled()) {
            log.warn("Using fallback for query[" + query + "]", e);
        }

        return new ProviderResponse(new ArrayList<>());
    }

    @RequestMapping(value = "/history", method = GET)
    @ResponseBody
    public List<String> getHistory(@RequestHeader("x-user-id") String userId) {
        return cacheService.getUserHistoryValues(userId);
    }
}
