package biz.donvi.jakesRTP1API.configuration.distributions;

public interface Symmetric {
    int getRadiusMax();

    void setRadiusMax(int radiusMax);

    int getRadiusMin();

    void setRadiusMin(int radiusMin);

    boolean isGaussianDistribution();

    void setGaussianDistribution(boolean gaussianDistribution);

    double getGaussianShrink();

    void setGaussianShrink(double gaussianShrink);

    double getGaussianCenter();

    void setGaussianCenter(double gaussianCenter);
}
