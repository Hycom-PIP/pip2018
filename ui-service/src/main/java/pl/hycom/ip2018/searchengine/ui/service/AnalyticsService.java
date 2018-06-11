package pl.hycom.ip2018.searchengine.ui.service;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.hycom.ip2018.searchengine.ui.model.PhraseStatistics;
import pl.hycom.ip2018.searchengine.ui.model.StatisticsResult;
import pl.hycom.ip2018.searchengine.ui.model.ViewsNumberResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class AnalyticsService {

    private Analytics analytics;
    private String profileId;
    private static final String PAGE_VIEWS = "ga:pageviews";

    /**
     * Initialize Google Analytics service by getting credentials and profileId
     * @throws IOException in case of invalid credentials
     */
    public AnalyticsService() throws IOException{
        try {
            GoogleAnalyticsAuth auth = new GoogleAnalyticsAuth();
            analytics = auth.initializeAnalytics();
            profileId = auth.getFirstProfileId(analytics);
        } catch (GeneralSecurityException e) {
            if(log.isErrorEnabled()) {
                log.error("Error initializing analytics credentials", e);
            }
            throw new IOException(e);
        }
    }

    /**
     * @param daysAgo number indicating how many days before take into consideration
     * @return Result object with start and endTime and number of visits
     * @throws IOException in case of error in executing query to analytics
     */
    public ViewsNumberResult getNumberOfViewsInPeriod(String daysAgo) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Requesting number of views from Google Analytics for site");
        }
        GaData result = analytics.data().ga()
                .get("ga:" + profileId, daysAgo + "daysAgo", "today", PAGE_VIEWS)
                .execute();
        int viewsNumber = Integer.parseInt(result.getTotalsForAllResults().get(PAGE_VIEWS));

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(Integer.parseInt(daysAgo));
        return new ViewsNumberResult(viewsNumber, startDate, endDate);
    }

    /**
     * @param daysAgo number indicating how many days before take into consideration
     * @return Result object with start and endTime and pairs (query, number of searches)
     * @throws IOException in case of error in executing query to analytics
     */
    public StatisticsResult getStatisticsFromPeriod(String daysAgo) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("Requesting statistics from Google Analytics for site");
        }
        GaData result = analytics.data().ga()
                .get("ga:" + profileId, daysAgo + "daysAgo", "today",PAGE_VIEWS)
                .setDimensions("ga:pagePath")
                .setSort("-ga:pageviews")
                .execute();

        Map<String, PhraseStatistics> specificPagesViews = new LinkedHashMap<>();
        int totalViews = 0;

        for(List<String> specificPage : result.getRows()) {
            String specificSite = specificPage.get(0);
            if(!specificSite.contains("query"))
                continue;
            int specificSiteViews = Integer.parseInt(specificPage.get(1));
            String query = trimSiteToQuery(specificSite);
            specificPagesViews.put(query, new PhraseStatistics(specificSiteViews, 0));
            totalViews += specificSiteViews;
        }
        if(totalViews == 0) {
            throw new ArithmeticException("Division by zero");
        }
        for(Map.Entry<String, PhraseStatistics> specificPage : specificPagesViews.entrySet()) {
            PhraseStatistics phraseStatistics = specificPagesViews.get(specificPage.getKey());
            double percent = (double)phraseStatistics.getViewsNumber() / totalViews;
            specificPagesViews.get(specificPage.getKey()).setViewsNumberOfTotal(percent);
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
