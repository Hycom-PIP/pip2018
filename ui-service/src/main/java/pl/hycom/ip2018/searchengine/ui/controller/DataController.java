package pl.hycom.ip2018.searchengine.ui.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hycom.ip2018.searchengine.ui.service.AggregateServiceFeignClient;

@RestController
public class DataController {

    @Autowired
    private AggregateServiceFeignClient aggregateServiceFeignClient;

    @RequestMapping(value = "/history-data", method = GET)
    public List<String> historyData() {
        return aggregateServiceFeignClient.getHistory();
    }
}
