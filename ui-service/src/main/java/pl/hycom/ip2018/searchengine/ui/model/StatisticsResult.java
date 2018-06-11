package pl.hycom.ip2018.searchengine.ui.model;

import javafx.util.Pair;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class StatisticsResult {

    private Map<String, Integer> phrases = new LinkedHashMap<>();
    private LocalDate startDate;
    private LocalDate endDate;

    public static final String PHRASES_KEY = "phrases";
    public static final String PHRASE_KEY = "phrase";
    public static final String SEARCHES_NUMBER_KEY = "searchesNumber";
    public static final String START_DATE_KEY = "startDate";
    public static final String END_DATE_KEY = "endDate";

    public StatisticsResult() {
        startDate = LocalDate.now();
        endDate = LocalDate.now();
    }

    public StatisticsResult(Map<String, Integer> phrases, LocalDate startDate, LocalDate endDate) {
        this.phrases = phrases;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
