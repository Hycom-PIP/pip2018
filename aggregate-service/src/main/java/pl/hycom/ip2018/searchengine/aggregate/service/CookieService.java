package pl.hycom.ip2018.searchengine.aggregate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Slf4j
@Service
public class CookieService {

    private static final String QUERY_COOKIE_NAME = "query";

    public Cookie createCookieWithQuery(String query) {
        Cookie cookie = new Cookie(QUERY_COOKIE_NAME, query);

        return cookie;
    }
}
