package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1.configuration.distributions.Circle;
import biz.donvi.jakesRTP1.configuration.distributions.RectangleImpl;
import biz.donvi.jakesRTP1.configuration.distributions.Square;
import biz.donvi.jakesRTP1.util.CoolDownTrackerImpl;
import biz.donvi.jakesRTP1API.configuration.ConfigurationFactory;
import biz.donvi.jakesRTP1API.configuration.DistributionProfile;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;
import biz.donvi.jakesRTP1API.util.CoolDownTracker;

public class ConfigurationFactoryImpl extends ConfigurationFactory {

    static void buildFactory() {ConfigurationFactory.factory = new ConfigurationFactoryImpl();}

    private ConfigurationFactoryImpl() {}

    @Override
    public RtpProfile newRtpProfileW(String name) {
        return new RtpProfileImpl(name);
    }

    @Override
    public DistributionProfile newDistributionProfileW(String shapeName) {
        DistributionProfile profile = new DistributionProfileImpl();
        if (shapeName != null) profile.setShape(newDistributionW(shapeName));
        return profile;
    }

    @Override
    public DistributionProfile newDistributionProfileW(Distribution baseDistribution) {
        DistributionProfile profile = new DistributionProfileImpl();
        profile.setShape(baseDistribution);
        return profile;
    }

    @Override
    public Distribution newDistributionW(String shapeName) {
        // Not sure how nulls are handled in switches, so I explicitly handle it here.
        if (shapeName == null) return null;
        // Easy enough...
        return (Distribution) switch (shapeName) {
            case "square" -> new Square();
            case "circle" -> new Circle();
            case "rectangle" -> new RectangleImpl();
            default -> null;
        };
    }


    @Override
    protected CoolDownTracker newCoolDownTrackerW(float coolDownTimeInSeconds) {
        return new CoolDownTrackerImpl(coolDownTimeInSeconds);
    }


}
