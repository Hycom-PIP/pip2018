package pl.hycom.ip2018.searchengine.ui.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.ui.rest.inner.TestResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestRest {

    @RequestMapping(value = "test", method = GET)
    public int test() {
        return 12345;
    }

    @RequestMapping(value = "statistics", method = GET)
    public List<TestResult> statistics() {
        return Arrays.asList(
                new TestResult("studio", 6, 2.34f),
                new TestResult("color", 5, 1.95f),
                new TestResult("portrait", 4, 1.56f),
                new TestResult("hair", 3, 1.17f),
                new TestResult("leaves", 3, 1.17f),
                new TestResult("night", 3, 1.17f),
                new TestResult("weeding", 3, 1.17f),
                new TestResult("young", 3, 1.17f),
                new TestResult("aerial view", 2, 0.78f));
    }
}
