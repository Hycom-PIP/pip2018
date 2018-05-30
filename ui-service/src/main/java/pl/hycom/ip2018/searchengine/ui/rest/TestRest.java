package pl.hycom.ip2018.searchengine.ui.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.hycom.ip2018.searchengine.ui.rest.inner.TestResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestRest {

    @RequestMapping(value = "test", method = GET)
    public int test(@RequestParam("period") String period) {
        if ("7days".equals(period)) {
            return 123;
        } else if ("30days".equals(period)) {
            return 1234;
        } else if ("3months".equals(period)) {
            return 12345;
        } else {
            return 123456;
        }
    }

    @RequestMapping(value = "statistics", method = GET)
    public List<TestResult> statistics(@RequestParam("period") String period) {
        if ("7days".equals(period)) {
            return Collections.singletonList(
                    new TestResult("xd", 100, 100f));
        } else if ("30days".equals(period)) {
            return Arrays.asList(
                    new TestResult("tiger", 50, 50f),
                    new TestResult("t-34", 50, 50f));
        } else if ("3months".equals(period)) {
            return Arrays.asList(
                    new TestResult("eliza", 33, 33.33f),
                    new TestResult("marie", 33, 33.33f),
                    new TestResult("lindsey", 33, 33.33f));
        } else {
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
}
