package pl.hycom.ip2018.searchengine.ui.service;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import pl.hycom.ip2018.searchengine.ui.model.ViewsNumberResult;
import pl.hycom.ip2018.searchengine.ui.rest.inner.TestResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AnalyticsService {

    @Autowired
    private JsonResponse jsonConverter;

    public ViewsNumberResult getNumberOfViewsInPeriod(int daysAgo) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("Requesting number of views from Google Analytics for site");
        }
        String guery = "https://www.googleapis.com/analytics/v3/data/ga?ids=ga%3A175532907&start-date=" + daysAgo + "daysAgo&end-date=today&metrics=ga%3Apageviews";
        URL url = new URL(guery);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String response = buffer.lines().collect(Collectors.joining());
        int numberOfViews = Integer.parseInt(response);
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(daysAgo);
        return new ViewsNumberResult(numberOfViews, startDate, endDate);
    }

    public List<TestResult> getStatisticsFromPeriod(int daysAgo) {
        throw new NotImplementedException();
    }
}
