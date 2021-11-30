package biz.donvi.jakesRTP1.configuration.distributions;

import biz.donvi.jakesRTP1API.configuration.ConfigurationFactory;
import biz.donvi.jakesRTP1API.configuration.DistributionProfile;
import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;
import biz.donvi.jakesRTP1API.configuration.distributions.Rectangle;
import biz.donvi.jakesRTP1API.configuration.distributions.Symmetric;
import biz.donvi.jakesRTP1API.util.JrtpBaseException;
import org.bukkit.configuration.ConfigurationSection;

public class DistributionLoader {

    public static final DistributionLoader INSTANCE = new DistributionLoader();

    private DistributionLoader() {}

    public DistributionProfile loadDistributionProfile(ConfigurationSection settings)
    throws JrtpBaseException.ConfigurationException {
        DistributionProfile distP = ConfigurationFactory.newDistributionProfile(loadDistribution(settings));


        distP.setCenter(
            DistributionProfile.CenterTypes.values()[settings.getString("center.option", "a").charAt(0) - 'a']);
        distP.setCenterX(settings.getInt("center.c-custom.x"));
        distP.setCenterZ(settings.getInt("center.c-custom.z"));

        return distP;
    }

    public Distribution loadDistribution(ConfigurationSection settings)
    throws JrtpBaseException.ConfigurationException {
        String shapeName = settings.getString("shape", "").toLowerCase();

        Distribution shape = ConfigurationFactory.newDistribution(shapeName);

        switch (shapeName) {
            case "square", "circle" -> {
                Symmetric shapeS = (Symmetric) shape;
                shapeS.setRadiusMax(settings.getInt("radius.max", 2000));
                shapeS.setRadiusMin(settings.getInt("radius.min", 1000));
                shapeS.setGaussianDistribution(settings.getBoolean("gaussian-distribution.enabled", false));
                shapeS.setGaussianShrink(settings.getDouble("gaussian-distribution.shrink", 4));
                shapeS.setGaussianCenter(settings.getDouble("gaussian-distribution.center", 0.25));
            }
            case "rectangle" -> {
                Rectangle shapeR = (Rectangle) shape;
                shapeR.setXRadius(settings.getInt("size.x-width", 2000) / 2);
                shapeR.setZRadius(settings.getInt("size.z-width", 2000) / 2);
                shapeR.setGapEnabled(settings.getBoolean("gap.enabled"));
                shapeR.setGapXRadius(settings.getInt("gap.x-width", 700) / 2);
                shapeR.setGapZRadius(settings.getInt("gap.z-width", 700) / 2);
                shapeR.setGapXCenter(settings.getInt("gap.x-center", 150) / 2);
                shapeR.setGapZCenter(settings.getInt("gap.z-center", 150) / 2);
            }
        }
        return shape;
    }
}
