package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1API.configuration.DistributionProfile;
import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;
import biz.donvi.jakesRTP1API.util.DebugPrintable;
import biz.donvi.jakesRTP1API.util.MultiDebugPrintProvider;
import org.apache.commons.lang.NotImplementedException;

import java.util.Collections;
import java.util.List;

public class DistributionProfileImpl implements DistributionProfile, MultiDebugPrintProvider, DebugPrintable {

    private Distribution shape;
    private CenterTypes  center;
    private int          centerX;
    private int          centerZ;

    //// Delegating methods \\\\

    @Override
    public String shapeName() {return shape.shapeName();}

    @Override
    public int getArea() {return shape.getArea();}

    @Override
    public int[] generateCords() {return shape.getCords();}

    //// Getters & Setters \\\\

    public Distribution getShape() {
        return shape;
    }

    public void setShape(Distribution shape) {
        this.shape = shape;
    }

    @Override
    public CenterTypes getCenter() {return center;}

    @Override
    public void setCenter(CenterTypes center) {this.center = center;}

    @Override
    public int getCenterX() {return centerX;}

    @Override
    public void setCenterX(int centerX) {this.centerX = centerX;}

    @Override
    public int getCenterZ() {return centerZ;}

    @Override
    public void setCenterZ(int centerZ) {this.centerZ = centerZ;}

    //// MultiDebugPrintProvider \\\\

    @Override
    public List<String> getDebugPrinterNames() {
        return Collections.singletonList("default");
    }

    @Override
    public DebugPrintable getDebugPrinterFor(String name) {
        return this;
    }

    //// DebugPrintable \\\\

    @Override
    public String getDebugPrintableName() {return "default";}

    @Override
    public List<String> infoStrings() {
        throw new NotImplementedException();
    }


}
