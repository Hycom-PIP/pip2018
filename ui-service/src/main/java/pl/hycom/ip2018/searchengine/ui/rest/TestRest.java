package pl.hycom.ip2018.searchengine.ui.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.ui.model.StatisticsResult;
import pl.hycom.ip2018.searchengine.ui.model.ViewsNumberResult;
import pl.hycom.ip2018.searchengine.ui.service.AnalyticsService;
import pl.hycom.ip2018.searchengine.ui.service.JsonResponse;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Slf4j
public class TestRest {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private JsonResponse jsonConverter;

    /**
     * @param period number of days to take into consideration
     * @return json object containing {@link ViewsNumberResult}
     */
    @RequestMapping(value = "test", method = GET)
    public String test(@RequestParam("period") String period) {
        try {
            return jsonConverter.getAsString(analyticsService.getNumberOfViewsInPeriod(period));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to get number of views", e);
            }
            return jsonConverter.getAsString(new ViewsNumberResult());
        }
    }

    /**
     * @param period number of days to take into consideration
     * @return json object containing {@link ViewsNumberResult}
     */
    @RequestMapping(value = "statistics", method = GET)
    public String statistics(@RequestParam("period") String period) {
        try {
            return jsonConverter.getAsString(analyticsService.getStatisticsFromPeriod(period));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to get views of specific pages", e);
            }
            return jsonConverter.getAsString(new StatisticsResult());
        }
    }

    @RequestMapping(value = "historyMock", method = GET)
    public List<String> history() {
        return Stream.of("The Celtic Holocaust", "The Destroyer of Worlds", "Blueprint for Armageddon",
                "Painfotainment", "A Basic Look At Post-Modernism", "Structuralism and Mythology",
                "The Frankfurt School", "Schopenhauer", "Henry David Thoreau's views on the individual, society and civil disobedience"
        ).collect(Collectors.toList());
    }
}
