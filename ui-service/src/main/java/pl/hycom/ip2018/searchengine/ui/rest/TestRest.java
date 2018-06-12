package pl.hycom.ip2018.searchengine.ui.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.hycom.ip2018.searchengine.ui.model.PhraseStatistics;
import pl.hycom.ip2018.searchengine.ui.model.StatisticsResult;
import pl.hycom.ip2018.searchengine.ui.model.ViewsNumberResult;
import pl.hycom.ip2018.searchengine.ui.rest.inner.Result;
import pl.hycom.ip2018.searchengine.ui.service.AnalyticsService;

@RestController
@Slf4j
public class TestRest {

    @Autowired
    private AnalyticsService analyticsService;

    /**
     * @param period
     *            number of days to take into consideration
     * @return json object containing {@link ViewsNumberResult}
     */
    @RequestMapping(value = "test", method = GET)
    public int test(@RequestParam("period") String period) {
        try {
            return analyticsService.getNumberOfViewsInPeriod(period).getViewsNumber();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to get number of views", e);
            }
            return 0;
        }
    }

    /**
     * @param period
     *            number of days to take into consideration
     * @return json object containing {@link ViewsNumberResult}
     */
    @RequestMapping(value = "statistics", method = GET)
    public List<Result> statistics(@RequestParam("period") String period) {
        try {

            List<Result> results = new ArrayList<>();

            StatisticsResult stats = analyticsService.getStatisticsFromPeriod(period);

            for (Map.Entry<String, PhraseStatistics> entry : stats.getPhrases().entrySet()) {
                results.add(new Result(entry.getKey(),
                        entry.getValue().getViewsNumber(),
                        (float) entry.getValue().getViewsNumberOfTotal() * 100));
            }

            return results;

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to get views of specific pages", e);
            }
            return new ArrayList<>();
        }
    }
}
