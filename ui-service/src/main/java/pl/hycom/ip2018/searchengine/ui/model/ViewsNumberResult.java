package pl.hycom.ip2018.searchengine.ui.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ViewsNumberResult {

    private int viewsNumber;
    private LocalDate startDate;
    private LocalDate endDate;

    public ViewsNumberResult(final int viewsNumber, final LocalDate startDate, final LocalDate endDate) {
        this.viewsNumber = viewsNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
