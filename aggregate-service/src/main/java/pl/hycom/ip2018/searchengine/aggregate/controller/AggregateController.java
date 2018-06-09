package pl.hycom.ip2018.searchengine.aggregate.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import pl.hycom.ip2018.searchengine.aggregate.service.AggregateSearch;
import pl.hycom.ip2018.searchengine.aggregate.service.CacheService;
import pl.hycom.ip2018.searchengine.aggregate.service.CookieService;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

@RestController
@Api
@Slf4j
public class AggregateController {

    @Autowired
    private AggregateSearch aggregateSearch;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private CacheService cacheService;

    @HystrixCommand(fallbackMethod = "getMessageFallBack", commandKey = "Aggregate-Search-Service", groupKey = "GetMessage")
    @RequestMapping(value = "/res", method = GET)
    public ProviderResponse getMessage(@RequestParam("query") String query,
            @RequestParam List<String> providers,
            HttpServletResponse resp, HttpServletRequest req) {

        cacheService.addQueryToCache(cookieService.getUserId(req, resp), query);

        return aggregateSearch.getResponse(query, providers);
    }

    public ProviderResponse getMessageFallBack(String query, List<String> providers, HttpServletResponse response, HttpServletRequest req) {
        if (log.isWarnEnabled()) {
            log.warn("Using fallbac for query[" + query + "]");
        }

        return new ProviderResponse(new ArrayList<>());
    }

    @RequestMapping(value = "/history", method = GET)
    @ResponseBody
    public List<String> getHistory(HttpServletResponse resp, HttpServletRequest req) {
        return cacheService.getUserHistoryValues(cookieService.getUserId(req, resp));
    }
}
