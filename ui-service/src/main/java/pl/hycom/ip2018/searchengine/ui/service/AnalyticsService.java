package pl.hycom.ip2018.searchengine.ui.service;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.hycom.ip2018.searchengine.ui.model.StatisticsResult;
import pl.hycom.ip2018.searchengine.ui.model.ViewsNumberResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.hycom.ip2018.searchengine.ui.service.GoogleAnalyticsAuth.getFirstProfileId;
import static pl.hycom.ip2018.searchengine.ui.service.GoogleAnalyticsAuth.initializeAnalytics;

@Slf4j
@Component
public class AnalyticsService {

    private Analytics analytics;
    private String profileId;

    /**
     * Initialize Google Analytics service by getting credentials and profileId
     * @throws Exception in case of invalid credentials
     */
    public AnalyticsService() throws Exception{
        try {
            analytics = GoogleAnalyticsAuth.initializeAnalytics();
            profileId = getFirstProfileId(analytics);
        } catch (GeneralSecurityException e) {
            if(log.isErrorEnabled()) {
                log.error("Error initializing analytics credentials", e);
            }
            throw new Exception(e);
        }
    }

    /**
     * @param daysAgo number indicating how many days before take into consideration
     * @return Result object with start and endTime and number of visits
     * @throws Exception in case of error in executing query to analytics
     */
    public ViewsNumberResult getNumberOfViewsInPeriod(String daysAgo) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("Requesting number of views from Google Analytics for site");
        }
        GaData result = analytics.data().ga()
                .get("ga:" + profileId, daysAgo + "daysAgo", "today", "ga:pageviews")
                .execute();
        int viewsNumber = Integer.parseInt(result.getTotalsForAllResults().get("ga:pageviews"));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(Integer.parseInt(daysAgo));
        return new ViewsNumberResult(viewsNumber, startDate, endDate);
    }

    /**
     * @param daysAgo number indicating how many days before take into consideration
     * @return Result object with start and endTime and pairs (query, number of searches)
     * @throws Exception in case of error in executing query to analytics
     */
    public StatisticsResult getStatisticsFromPeriod(String daysAgo) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("Requesting statistics from Google Analytics for site");
        }
        GaData result = analytics.data().ga()
                .get("ga:" + profileId, daysAgo + "daysAgo", "today","ga:pageviews")
                .setDimensions("ga:pagePath")
                .setSort("-ga:pageviews")
                .execute();
        Map<String, Integer> specificPagesViews = new LinkedHashMap<>();
        for(List<String> specificPage : result.getRows()) {
            String specificSite = specificPage.get(0);
            if(!specificSite.contains("query"))
                continue;
            String specificSiteViews = specificPage.get(1);
            String query = trimSiteToQuery(specificSite);
            specificPagesViews.put(query, Integer.parseInt(specificSiteViews));
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(Integer.parseInt(daysAgo));
        return new StatisticsResult(specificPagesViews, startDate, endDate);
    }

    /**
     * @param site containing full site URL
     * @return only query parameter from whole URL
     */
    private String trimSiteToQuery(String site) {
        site = site.substring(site.indexOf("query=") + "query=".length());
        int andPosition = site.indexOf('&');
        if(andPosition == -1)
            andPosition = site.length();
        return site.substring(0, andPosition);
    }
}
