package biz.donvi.jakesRTP1.configuration.distributions;

import org.bukkit.configuration.ConfigurationSection;

public class Square extends SymmetricImpl {

    @Override
    public String shapeName() {return "square";}

    @Override
    public int getArea() {
        return 4 * (radiusMax * radiusMax - radiusMin * radiusMin);
    }

    @Override
    public int[] getCords() {
        return asIntArray2w(
            !gaussianDistribution
                ? getRandXySquare(radiusMax, radiusMin)
                : getRandXySquare(radiusMax, radiusMin, gaussianShrink, gaussianCenter)
        );
    }
}
