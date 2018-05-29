package pl.hycom.ip2018.searchengine.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// TODO Example controller, delete it
@RestController
public class UiController {

    @Autowired
    Environment env;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String getMessage() {
        return "HI FROM UI FROM PORT " + env.getProperty("server.port");
    }
}
