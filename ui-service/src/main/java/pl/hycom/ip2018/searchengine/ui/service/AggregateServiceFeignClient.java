package pl.hycom.ip2018.searchengine.ui.service;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

@FeignClient(name = "aggregate-service")
public interface AggregateServiceFeignClient {

    @RequestMapping(value = "/res", method = GET)
    ProviderResponse getMessage(@RequestParam("query") String query, @RequestParam("provider") List<String> provider, @RequestHeader("x-user-id") String userId);

    @RequestMapping(value = "/history", method = GET)
    List<String> getHistory(@RequestHeader("x-user-id") String userId);

}
