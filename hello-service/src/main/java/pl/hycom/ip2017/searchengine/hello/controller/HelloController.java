package pl.hycom.ip2017.searchengine.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2017.searchengine.hello.service.HelloService;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String getMessage() {
        return helloService.getMessage();
    }
}
