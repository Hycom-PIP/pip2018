package pl.hycom.ip2018.searchengine.aggregate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CookieService {

    private static final String QUERY_COOKIE_NAME = "query";

    private Cookie[] cookies;

    public Cookie createCookieWithQuery(String query) {
        List<String> queriesList = new ArrayList<>(getQueryCookies());
        queriesList.add(query);

//        https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        Cookie cookie = new Cookie(QUERY_COOKIE_NAME, queriesList.toString());
        return cookie;
    }

    public void readCurrentCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    private List<String> getQueryCookies() {
        List<String> result = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(QUERY_COOKIE_NAME))
                result.add(cookie.getValue().replaceAll("\\p{P}",""));
        }
        return result;
    }
}
