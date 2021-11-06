package biz.donvi.jakesRTP1.configuration.distributions;

import biz.donvi.JakesRTP1.configuration.distributions.Symmetric;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public abstract class SymmetricImpl extends AbstractDistribution implements Symmetric {
    protected int     radiusMax            = 1000;
    protected int     radiusMin            = 2000;
    protected boolean gaussianDistribution = false;
    protected double  gaussianShrink       = 4;
    protected double  gaussianCenter       = 0.25;

    @Override
    public int getRadiusMax() {
        return radiusMax;
    }

    @Override
    public void setRadiusMax(int radiusMax) {
        this.radiusMax = radiusMax;
    }

    @Override
    public int getRadiusMin() {
        return radiusMin;
    }

    @Override
    public void setRadiusMin(int radiusMin) {
        this.radiusMin = radiusMin;
    }

    @Override
    public boolean isGaussianDistribution() {
        return gaussianDistribution;
    }

    @Override
    public void setGaussianDistribution(boolean gaussianDistribution) {
        this.gaussianDistribution = gaussianDistribution;
    }

    @Override
    public double getGaussianShrink() {
        return gaussianShrink;
    }

    @Override
    public void setGaussianShrink(double gaussianShrink) {
        this.gaussianShrink = gaussianShrink;
    }

    @Override
    public double getGaussianCenter() {
        return gaussianCenter;
    }

    @Override
    public void setGaussianCenter(double gaussianCenter) {
        this.gaussianCenter = gaussianCenter;
    }

    @Override
    public List<String> infoStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Distribution shape: " + shapeName());
        list.add("Radius max: " + radiusMax + " | Radius min: " + radiusMin);
        list.add("Gaussian Distribution: " + (gaussianDistribution ? " Enabled" : "Disabled"));
        if (gaussianDistribution) {
            list.add("Gaussian shrink: " + gaussianShrink);
            list.add("Gaussian center: " + gaussianCenter);
        }
        return list;
    }
}
