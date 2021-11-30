package biz.donvi.jakesRTP1API.configuration.distributions;

public interface Rectangle {
    int getXRadius();

    void setXRadius(int xRadius);

    int getZRadius();

    void setZRadius(int zRadius);

    boolean isGapEnabled();

    void setGapEnabled(boolean gapEnabled);

    int getGapXRadius();

    void setGapXRadius(int gapXRadius);

    int getGapZRadius();

    void setGapZRadius(int gapZRadius);

    int getGapXCenter();

    void setGapXCenter(int gapXCenter);

    int getGapZCenter();

    void setGapZCenter(int gapZCenter);
}
