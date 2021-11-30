package biz.donvi.jakesRTP1API.configuration;

import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;
import biz.donvi.jakesRTP1API.configuration.distributions.Rectangle;
import biz.donvi.jakesRTP1API.configuration.distributions.Symmetric;
import biz.donvi.jakesRTP1API.util.CoolDownTracker;

/**
 * This class provides static methods to create all JRTP related components to make a valid {@link RtpProfile} from
 * scratch. [TODO add configuration loaders to this]
 * The process of creating a valid {@link RtpProfile} is as follows:
 * First create the profile using the factory methods:
 * <pre>
 *     // Create a new profile. Some values will need to be set before it is valid.
 *     {@link RtpProfile} profile = new {@link ConfigurationFactory#newRtpProfile(String)
 *     ConfigurationFactory.newRtpProfile}("myProfile");
 *
 *     // Set landing world to the world called `world`, and allow rtp from all worlds.
 *     profile{@link RtpProfile#setLandingWorld .setLandingWorld}(Bukkit.getServer().getWorld("world"));
 *     profile{@link RtpProfile#setCallFromWorlds .setCallFromWorlds}(Bukkit.getServer().getWorlds());
 *
 *     // Create a default DistributionSettings with circle as its shape.
 *     {@link DistributionProfile} dist = {@link ConfigurationFactory#newDistributionProfile(String)
 *     ConfigurationFactory.newDistributionProfile}("circle");
 *     // OR retrieve the distribution from some other RtpProfile
 *     dist = otherProfile{@link RtpProfile#getDistribution .getDistribution()};
 *     profile{@link RtpProfile#setDistribution .setDistribution}(dist);
 *
 *     // Set the cool-down. This can also be taken from another RtpProfile.
 *     // (so that using one will put you on cool-down for the other.)
 *     profile{@link RtpProfile#setCoolDownTime(float) .setCoolDownTime}(5);
 *     // OR
 *     profile{@link RtpProfile#setCoolDown(CoolDownTracker) .setCoolDown}({@link
 *     ConfigurationFactory#newCoolDownTracker(float) ConfigurationFactory.newCoolDownTracker}(5));
 *
 *     // Lastly, check the validity. It should be good
 *     if (!profile{@link RtpProfile#isValid() .isValid()}) {
 *         // do something
 *     }
 * </pre>
 * [TODO] A profile can also be manually loaded from a file as such:
 */
public abstract class ConfigurationFactory {

    /**
     * The configuration factory. This will be set by JRTP-Core on load.
     **/
    protected static ConfigurationFactory factory;

    /**
     * Returns a new, empty {@link RtpProfile} with the given name, and all values set to their defaults. The given
     * profile is not guaranteed to be valid upon creation. To test validity, use {@link RtpProfile#isValid()}. To
     * make the profile valid, make sure all object references are set. At the time of writing this documentation,
     * the four properties that need to be set are {@code landingWorld}, {@code callFromWorlds}, {@code distribution},
     * and {@code coolDown}, though this may change over time. For creating a distribution, see
     * {@link #newDistributionProfile(String)}.
     *
     * @param name The name of the RtpProfile.
     * @return A new, mostly blank {@link RtpProfile}.
     */
    public static RtpProfile newRtpProfile(String name) {
        return factory.newRtpProfileW(name);
    }

    protected abstract RtpProfile newRtpProfileW(String name);

    /**
     * Returns a new {@link DistributionProfile} with default values set. If a shape name is given, this profile will
     * be created with a default {@link Distribution} for that shape will be included in it. This profile can be
     * added to an {@link RtpProfile} to specify where it will generate random points.
     * Read {@link #newDistribution(String)} for more information on the distribution shapes.
     *
     * @param shapeName The type of shape to start this profile with. Set as {@code null} to leave empty.
     * @return a new, default {@link DistributionProfile} that may or may not have it's {@link Distribution} set.
     */
    public static DistributionProfile newDistributionProfile(String shapeName) {
        return factory.newDistributionProfileW(shapeName);
    }

    protected abstract DistributionProfile newDistributionProfileW(String shapeName);

    public static DistributionProfile newDistributionProfile(Distribution baseDistribution) {
        return factory.newDistributionProfileW(baseDistribution);
    }

    protected abstract DistributionProfile newDistributionProfileW(Distribution baseDistribution);

    /**
     * Returns a new {@link Distribution} with default values for the given string. If an invalid string is given,
     * {@code null} will be returned. Currently, three valid strings are accepted: {@code circle} (can be cast as
     * {@link Symmetric}), {@code square} (can be cast as {@link Symmetric}), and {@code rectangle} (can be cast as
     * {@link Rectangle}).
     *
     * @param shapeName The name of the type of shape to create.
     * @return Either a valid {@link Distribution}, or null.
     */
    public static Distribution newDistribution(String shapeName) {
        return factory.newDistributionW(shapeName);
    }

    protected abstract Distribution newDistributionW(String shapeName);

    /**
     * Returns a new {@link CoolDownTracker} with the specified cool-down.
     * No further modifications need to be made before this can be used.
     *
     * @param coolDownTimeInSeconds The amount of time (in seconds) for the cool-down.
     * @return a new {@link CoolDownTracker} with the specified cool-down.
     */
    public static CoolDownTracker newCoolDownTracker(float coolDownTimeInSeconds) {
        return factory.newCoolDownTrackerW(coolDownTimeInSeconds);
    }

    protected abstract CoolDownTracker newCoolDownTrackerW(float coolDownTimeInSeconds);


}
