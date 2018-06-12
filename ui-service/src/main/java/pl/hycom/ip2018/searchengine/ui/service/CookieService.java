package pl.hycom.ip2018.searchengine.ui.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CookieService {

    private static final int COOKIE_MAX_AGE = 366 * 24 * 3600;

    private static final String COOKIE_NAME = "USER_ID";

    public String getUserId(HttpServletRequest req, HttpServletResponse resp) {
        if (shouldCreateCookie(req)) {
            Cookie cookie = createCookieWithUserId();
            resp.addCookie(cookie);
            return cookie.getValue();
        }

        return getUserIdFromRequest(req);
    }

    private boolean shouldCreateCookie(final HttpServletRequest req) {
        return req.getCookies() == null || Arrays.stream(req.getCookies()).noneMatch(cookie -> COOKIE_NAME.equals(cookie.getName()));
    }

    private Cookie createCookieWithUserId() {
        String generatedUUID = UUID.randomUUID().toString().replace("-", "");
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Cookie cookie = new Cookie(COOKIE_NAME, generatedUUID + '-' + currentDate);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    private String getUserIdFromRequest(HttpServletRequest req) {
        return Arrays.stream(req.getCookies())
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny()
                .orElse(null);
    }
}
