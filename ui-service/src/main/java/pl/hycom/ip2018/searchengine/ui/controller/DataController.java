package pl.hycom.ip2018.searchengine.ui.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.hycom.ip2018.searchengine.ui.service.AggregateServiceFeignClient;
import pl.hycom.ip2018.searchengine.ui.service.CookieService;

@RestController
public class DataController {

    @Autowired
    private CookieService cookieService;

    @Autowired
    private AggregateServiceFeignClient aggregateServiceFeignClient;

    @RequestMapping(value = "/history-data", method = GET)
    public List<String> historyData(HttpServletRequest req, HttpServletResponse resp) {
        return aggregateServiceFeignClient.getHistory(cookieService.getUserId(req, resp));
    }
}
