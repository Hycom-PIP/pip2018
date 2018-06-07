package pl.hycom.ip2018.searchengine.aggregate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class CookieService {

    private static final String USER_ID_COOKIE_NAME = "USER_ID";

    private Cookie[] cookies;

    public void createCookiesIfDoNotExist(HttpServletResponse response, HttpServletRequest req) {
        this.readCurrentCookies(req.getCookies());
        if (this.shouldCreateCookie())
            response.addCookie(this.createCookieWithUserId());
    }

    private void readCurrentCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    private boolean shouldCreateCookie() {
        if (cookies == null)
            return true;

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(USER_ID_COOKIE_NAME))
                return false;
        }
        return true;
    }

    private Cookie createCookieWithUserId() {
        String generatedUUID = UUID.randomUUID().toString().replace("-", "");
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String cookieValue = generatedUUID + currentDate;
        Cookie cookie = new Cookie(USER_ID_COOKIE_NAME, cookieValue);
        int secondsInYear = 366 * 24 * 3600;
        cookie.setMaxAge(secondsInYear);
        return cookie;
    }
}
