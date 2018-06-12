package pl.hycom.ip2018.searchengine.ui.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = { "/ui/", "/" }, method = GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = { "/ui/analytics", "/analytics" }, method = GET)
    public String analytics() {
        return "analytics";
    }

    @RequestMapping(value = { "/history", "/history" }, method = GET)
    public String history() {
        return "history";
    }

    @RequestMapping(value = { "/ui/results", "/results" }, method = GET)
    public String results() {
        return "results";
    }
}
