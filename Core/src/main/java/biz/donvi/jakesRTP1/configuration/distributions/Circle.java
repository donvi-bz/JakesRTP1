package biz.donvi.jakesRTP1.configuration.distributions;

import org.bukkit.configuration.ConfigurationSection;

public class Circle extends SymmetricImpl {
    @Override
    public String shapeName() {return "circle";}

    @Override
    public int getArea() {
        return (int) Math.floor(Math.PI * radiusMax * radiusMax - Math.PI * radiusMin * radiusMin);
    }

    @Override
    public int[] getCords() {
        return asIntArray2w(
            getRandXyCircle(radiusMax, radiusMin, gaussianShrink, gaussianCenter)
        );
    }
}
