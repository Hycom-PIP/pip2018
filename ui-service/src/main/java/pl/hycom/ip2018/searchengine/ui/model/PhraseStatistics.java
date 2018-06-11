package pl.hycom.ip2018.searchengine.ui.model;

public class PhraseStatistics {
    private int viewsNumber;
    private double viewsNumberOfTotal;

    public PhraseStatistics(int specificSiteViews, double viewsOfTotal) {
        this.viewsNumber = specificSiteViews;
        this.viewsNumberOfTotal = viewsOfTotal;
    }

    public void setViewsNumberOfTotal(double viewsNumberOfTotal) {
        this.viewsNumberOfTotal = viewsNumberOfTotal;
    }

    public int getViewsNumber() {
        return viewsNumber;
    }
}
