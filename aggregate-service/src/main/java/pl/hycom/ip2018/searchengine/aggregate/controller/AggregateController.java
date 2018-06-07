package pl.hycom.ip2018.searchengine.aggregate.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.aggregate.service.AggregateSearch;
import pl.hycom.ip2018.searchengine.aggregate.service.CacheService;
import pl.hycom.ip2018.searchengine.aggregate.service.CookieService;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Api
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
                                       @RequestParam List<String> provider,
                                       HttpServletResponse response, HttpServletRequest req
    ) {
        cookieService.createCookiesIfDoNotExist(response, req);
        String userId = cookieService.getUserId();
        cacheService.addQueryToCache(userId, query);
        return aggregateSearch.getResponse(query, provider);
    }

    public ProviderResponse getMessageFallBack(String query, List<String> providers,
                                               HttpServletResponse response, HttpServletRequest req
    ) {
        return new ProviderResponse(new ArrayList<>());
    }

}


//    @RequestParam("drive") String drive,
//    @RequestParam("wikipedia") String wikipedia,
//    @RequestParam("local") String local