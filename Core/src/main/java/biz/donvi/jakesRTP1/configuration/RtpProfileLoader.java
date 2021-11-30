package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1.configuration.distributions.DistributionLoader;
import biz.donvi.jakesRTP1API.configuration.ConfigurationFactory;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.util.JrtpBaseException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class RtpProfileLoader {


    public static final RtpProfileLoader loader = new RtpProfileLoader();

    private static final RtpProfile defaultRtpProfile = new RtpProfileImpl("__INTERNAL_DEFAULT_SETTINGS__");

    private RtpProfileLoader() {}


    public RtpProfile load(
        String name, ConfigurationSection config, RtpProfile defaults, RtpProfileLinker linker, Consumer<String> logger
    )
    throws JrtpBaseException.ConfigurationException {
        if (defaults == null) defaults = defaultRtpProfile;

        final String nameInLog = "[" + name + "] ";
        logger.accept("Loading rtpSettings...");

        // config version check
        final int[] configVersion = Arrays.stream(config.getString("$version", "0").split("\\."))
                                          .mapToInt(Integer::parseInt).toArray();

        final RtpProfileImpl profile = new RtpProfileImpl(name, configVersion);

        // Command Enabled
        profile.setCommandEnabled(config.getBoolean("command-enabled", defaults.isCommandEnabled()));
        logger.accept(nameInLog + "Command: " + (profile.isCommandEnabled() ? "Enabled" : "Disabled"));

        // Require Explicit Permission
        profile.setRequireExplicitPermission(config.getBoolean(
            "require-explicit-permission", defaults.isRequireExplicitPermission()));
        logger.accept(nameInLog + "Require explicit permission node: " + (
            profile.isRequireExplicitPermission()
                ? "True (" + RtpProfileImpl.EXPLICIT_PERM_PREFIX + name + ")"
                : "False"
        ));

        // Priority
        profile.setPriority((float) config.getDouble("priority", defaults.getPriority()));
        logger.accept(nameInLog + "Priority: " + profile.getPriority());

        // Landing World
        @SuppressWarnings("ConstantConditions") // It's guaranteed to be safe
        World landingWorld = config.getString("landing-world", null) == null ? null
            : Bukkit.getServer().getWorld(config.getString("landing-world", null));
        if (landingWorld == null) landingWorld = defaults.getLandingWorld();
        if (landingWorld == null) throw new JrtpBaseException.ConfigurationException("Landing world not recognised.");
        profile.setLandingWorld(landingWorld);
        logger.accept(nameInLog + "The user will land in the following world: " + landingWorld.getName());

        // Call From Worlds
        ArrayList<World> tempCallFromWorlds = new ArrayList<>();
        for (String callFromWorld : config.getStringList("call-from-worlds"))
            for (World testByWorld : Bukkit.getServer().getWorlds())
                if (!tempCallFromWorlds.contains(testByWorld) &&
                    Pattern.compile(callFromWorld).matcher(testByWorld.getName()).matches()
                ) tempCallFromWorlds.add(testByWorld);
        if (tempCallFromWorlds.size() == 0)
            if (defaults.getCallFromWorlds() != null) tempCallFromWorlds.addAll(defaults.getCallFromWorlds());
            else tempCallFromWorlds.add(landingWorld);
        profile.setCallFromWorlds(tempCallFromWorlds);

        logger.accept(nameInLog + "Call from worlds: ");
        for (World w : tempCallFromWorlds)
            logger.accept(nameInLog + "  - " + w.getName());

        // Distribution v1
        boolean distIsNull = config.getObject("distribution", Object.class) == null;
        String distAsName = config.getString("distribution", null);
        ConfigurationSection distAsConfig = config.getConfigurationSection("distribution");
        String distLoadFashion;

        // If everything is null, then we run with the default value
        if (distIsNull && distAsName == null && distAsConfig == null) {
            profile.setDistribution(defaults.getDistribution());
            distLoadFashion = "provided defaults";
        }
        // If we have a name, then we link and move on
        else if (distAsName != null) {
            linker.linkDistribution(profile, distAsName);
            distLoadFashion = "linking by name (" + distAsName + ")";
        }
        // If we have a config section, we pass the loading on to the next section.
        else if (distAsConfig != null) {
            profile.setDistribution(DistributionLoader.INSTANCE.loadDistributionProfile(distAsConfig));
            distLoadFashion = "config section";
        } else throw new JrtpBaseException.ConfigurationException("Distribution isn't null but also isn't useful");

        logger.accept(nameInLog + "Loaded distribution via " + distLoadFashion); // double log?!

        // Cool-Down
        double coolDownAsNum = config.getDouble("cooldown", Double.MIN_VALUE);
        String coolDownLit = config.getString("cooldown", null);
        boolean coolDownIsBorrowed = coolDownAsNum == Double.MIN_VALUE;
        // If no valid time was given, we either must have a name, or we result to the default.
        if (coolDownIsBorrowed)
            // If we DO have a name, we can link that one
            if (coolDownLit != null) linker.linkCoolDown(profile, coolDownLit);
                // If we DON'T, we take the default one
            else profile.setCoolDown(defaults.getCoolDown());
            // Otherwise, we were given a valid time and can use that.
        else profile.setCoolDownTime((float) coolDownAsNum);
        // Teensy bit of stupid-ness, if loading from version 0, we must make sure our cool-down is owned. To do this,
        // we just re-assign it. The coolDown MUST be linked by this point though!!!
        if (configVersion.length >= 1 && configVersion[0] == 0)
            profile.setCoolDown(profile.getCoolDown());

        if (coolDownIsBorrowed) logger.accept(nameInLog + "Cool-down is shared with settings `" +
                                              (coolDownLit != null ? coolDownLit : defaults.getName()) + "`.");
        String coolDownAsWords = coolDownIsBorrowed && coolDownLit != null && profile.getCoolDown() == null
            ? "[waiting-for-linking-step]" : String.valueOf(profile.getCoolDownTime());
        logger.accept(nameInLog + "Cool-down time: " + coolDownAsWords + " seconds.");

        // Warm-Up
        profile.setWarmup(config.getInt("warmup.time", defaults.getWarmup()));
        profile.setWarmupCancelOnMove(config.getBoolean("warmup.cancel-on-move", defaults.isWarmupCancelOnMove()));
        profile.setWarmupCountDown(config.getBoolean("warmup.count-down", defaults.isWarmupCountDown()));
        if (profile.getWarmup() > 0) {
            logger.accept(nameInLog + "Warmup Enabled | Warmup time: " + profile.getWarmup());
            logger.accept(nameInLog + "Cancel warmup on player move: " + profile.isWarmupCancelOnMove());
            logger.accept(nameInLog + "Count down to teleport: " + profile.isWarmupCountDown());
        } else logger.accept(nameInLog + "Warmup Disabled.");

        // Cost
        profile.setCost(config.getDouble("cost", defaults.getCost()));
        if (profile.getCost() > 0) logger.accept(nameInLog + "Cost: " + profile.getCost());
        else logger.accept(nameInLog + "Cost disabled.");

        // Bounds
        profile.setLowBound(config.getInt("bounds.low", defaults.getLowBound()));
        profile.setHighBound(config.getInt("bounds.high", defaults.getHighBound()));
        logger.accept(nameInLog + "Low bound: " + profile.getLowBound() + " | High bound: " + profile.getHighBound());

        // Check Radius
        profile.setCheckRadiusXZ(config.getInt("check-radius.x-z", defaults.getCheckRadiusXZ()));
        profile.setCheckRadiusVert(config.getInt("check-radius.vert", defaults.getCheckRadiusVert()));
        logger.accept(nameInLog +
                      "Check radius x and z: " + profile.getCheckRadiusXZ() +
                      " | Vert: " + profile.getCheckRadiusVert());

        // Max Attempts
        profile.setMaxAttempts(config.getInt("max-attempts.value", defaults.getMaxAttempts()));
        logger.accept(nameInLog + "Max Attempts: " + profile.getMaxAttempts());

        // Prefer sync tp - on command
        profile.setPreferSyncTpOnCommand(
            config.getBoolean("prefer-sync-tp.command", defaults.isPreferSyncTpOnCommand()));
        logger.accept(nameInLog + "Prefer sync tp on command: " + profile.isPreferSyncTpOnCommand());

        // Location Caching
        profile.setCacheLocationCount(config.getInt("preparations.cache-locations", defaults.getCacheLocationCount()));
        logger.accept(nameInLog + (profile.canUseLocQueue()
            ? "Location caching: Enabled | Num: " + profile.getCacheLocationCount()
            : "Location caching: Disabled"));

        // Location check profile
        profile.setCheckProfile(config.getString("location-checking-profile", null) == null
                                    ? defaults.getCheckProfile()
                                    : RtpProfile.LocCheckProfiles.values()[
                                        config.getString("location-checking-profile", "a")
                                              .toLowerCase().charAt(0) - 'a']);
        logger.accept(nameInLog + "Location check profile: " + profile.getCheckProfile().toString() +
                      " (" + (char) (profile.getCheckProfile().ordinal() + 'a') + ")");

        // Commands To Run
        profile.setCommandsToRun(config.getStringList("then-execute").size() == 0
                                     ? defaults.getCommandsToRun()
                                     : config.getStringList("then-execute").toArray(String[]::new));

        // Very important that we register the profile!!!
        linker.registerProfile(profile);
        logger.accept(nameInLog + "Registering profile. (Loading complete)");
        return profile;
    }
}
