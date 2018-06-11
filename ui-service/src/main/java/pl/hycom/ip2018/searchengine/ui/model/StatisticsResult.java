package pl.hycom.ip2018.searchengine.ui.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class StatisticsResult {

    private Map<String, PhraseStatistics> phrases = new LinkedHashMap<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public StatisticsResult() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();
    }

    public StatisticsResult(Map<String, PhraseStatistics> phrases, LocalDate startDate, LocalDate endDate) {
        this.phrases = phrases;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
