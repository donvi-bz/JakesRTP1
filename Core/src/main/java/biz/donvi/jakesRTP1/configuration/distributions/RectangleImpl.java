package biz.donvi.jakesRTP1.configuration.distributions;

import biz.donvi.jakesRTP1API.configuration.distributions.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RectangleImpl extends AbstractDistribution implements Rectangle {

    protected int     xRadius    = 2000;
    protected int     zRadius    = 2000;
    protected boolean gapEnabled = false;
    protected int     gapXRadius = 700;
    protected int     gapZRadius = 700;
    protected int     gapXCenter = 150;
    protected int     gapZCenter = 150;

    @Override
    public int getXRadius() {
        return xRadius;
    }

    @Override
    public void setXRadius(int xRadius) {
        this.xRadius = xRadius;
    }

    @Override
    public int getZRadius() {
        return zRadius;
    }

    @Override
    public void setZRadius(int zRadius) {
        this.zRadius = zRadius;
    }

    @Override
    public boolean isGapEnabled() {
        return gapEnabled;
    }

    @Override
    public void setGapEnabled(boolean gapEnabled) {
        this.gapEnabled = gapEnabled;
    }

    @Override
    public int getGapXRadius() {
        return gapXRadius;
    }

    @Override
    public void setGapXRadius(int gapXRadius) {
        this.gapXRadius = gapXRadius;
    }

    @Override
    public int getGapZRadius() {
        return gapZRadius;
    }

    @Override
    public void setGapZRadius(int gapZRadius) {
        this.gapZRadius = gapZRadius;
    }

    @Override
    public int getGapXCenter() {
        return gapXCenter;
    }

    @Override
    public void setGapXCenter(int gapXCenter) {
        this.gapXCenter = gapXCenter;
    }

    @Override
    public int getGapZCenter() {
        return gapZCenter;
    }

    @Override
    public void setGapZCenter(int gapZCenter) {
        this.gapZCenter = gapZCenter;
    }

    @Override
    public String shapeName() {return "rectangle";}

    @Override
    public int getArea() {
        return xRadius * zRadius * 4 - (gapEnabled ? gapXRadius * gapZRadius * 4 : 0);
    }

    @Override
    public int[] getCords() {
        return asIntArray2w(
            !gapEnabled
                ? getRandXyRectangle(xRadius, zRadius)
                : getRandXyRectangle(xRadius, zRadius, gapZRadius, gapZRadius, gapXCenter, gapZCenter)
        );
    }

    @Override
    public List<String> infoStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Distribution shape: " + shapeName());
        list.add("X radius: " + xRadius + " | Z radius: " + zRadius);
        if (gapEnabled) {
            list.add("Gap: True");
            list.add("X radius: " + gapXRadius + " | Z radius: " + zRadius);
            list.add("X center: " + gapXCenter + " | Z center: " + gapZCenter);
        } else {
            list.add("Gap: False");
        }
        return list;
    }
}
