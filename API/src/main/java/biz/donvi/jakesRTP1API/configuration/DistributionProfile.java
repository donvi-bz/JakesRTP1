package biz.donvi.jakesRTP1API.configuration;

import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;

public interface DistributionProfile {

    public enum CenterTypes {WORLD_SPAWN, PLAYER_LOCATION, PRESET_VALUE}

    String shapeName();

    int getArea();

    int[] generateCords();

    CenterTypes getCenter();

    void setShape(Distribution shape);

    Distribution getShape();

    void setCenter(CenterTypes center);

    int getCenterX();

    void setCenterX(int centerX);

    int getCenterZ();

    void setCenterZ(int centerZ);

}
