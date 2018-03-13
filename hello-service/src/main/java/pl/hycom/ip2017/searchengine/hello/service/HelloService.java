package pl.hycom.ip2017.searchengine.hello.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getMessage() {
        return "Hello, World!";
    }
}
