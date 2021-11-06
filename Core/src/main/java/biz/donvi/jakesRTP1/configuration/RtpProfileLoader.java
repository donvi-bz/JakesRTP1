package biz.donvi.jakesRTP1.configuration;

import biz.donvi.JakesRTP1.configuration.ConfigurationFactory;
import biz.donvi.JakesRTP1.configuration.DistributionProfile;
import biz.donvi.JakesRTP1.configuration.RtpProfile;
import biz.donvi.JakesRTP1.util.CoolDownTracker;
import biz.donvi.JakesRTP1.util.JrtpBaseException;
import biz.donvi.jakesRTP1.util.CoolDownTrackerImpl;
import biz.donvi.jakesRTP1.util.GeneralUtil;
import biz.donvi.jakesRTP1.util.GeneralUtil.Pair;
import org.apache.commons.collections4.map.ReferenceMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.*;

public class RtpProfileLoader {


    public static final RtpProfileLoader loader = new RtpProfileLoader();

    private final RtpProfile defaultRtpProfile = ConfigurationFactory.newRtpProfile("__INTERNAL_DEFAULT_SETTINGS__");

    private RtpProfileLoader() {}


    public RtpProfile load(String name, ConfigurationSection config, RtpProfile defaults, Consumer<String> logger)
    throws JrtpBaseException.ConfigurationException {
        final RtpProfile profile = ConfigurationFactory.newRtpProfile(name);

        final String nameInLog = "[" + name + "] ";
        logger.accept("Loading rtpSettings...");

        // Command Enabled
        profile.setCommandEnabled(config.getBoolean("command-enabled", defaults.isCommandEnabled()));
        logger.accept(nameInLog + "Command: " + (profile.isCommandEnabled() ? "Enabled" : "Disabled"));

        // Require Explicit Permission
        profile.setRequireExplicitPermission(config.getBoolean(
            "require-explicit-permission", defaults.isRequireExplicitPermission()));
        logger.accept(nameInLog + "Require explicit permission node: " + (
            profile.isRequireExplicitPermission() ? "True (" + RtpProfileImpl.EXPLICIT_PERM_PREFIX + name + ")" : "False"
        ));

        // Priority
        profile.setPriority((float) config.getDouble("priority", defaults.getPriority()));
        logger.accept(nameInLog + "Priority: " + profile.getPriority());

        // Landing World
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

        for (String s : infoStringsCallFromWorlds(false)) logger.accept(nameInLog + s);

        // Distribution
        String distName = config.getString("distribution", null);
        if (distName == null) profile.setDistribution(defaults.getDistribution());
        else {
            if (distName.equalsIgnoreCase("world-border"))
                distName += "_" + landingWorld.getName();
            distribution = distributions.get(distName);
        }
        if (distribution == null) {
            StringBuilder strb = new StringBuilder();
            for (String s : distributions.keySet()) strb.append(" ").append(s);
            throw new JrtpBaseException.ConfigurationException(
                "Distribution not found. Distribution given: '" + config.getString("distribution") +
                "', distributions available:" + strb.toString());
        }
        for (String s : distribution.shape.infoStrings(false)) logger.accept(nameInLog + s);
        logger.accept(nameInLog + infoStringRegionCenter(false)); // double log!

        // Cool-Down
        coolDown = new CoolDownTrackerImpl((float) config.getDouble("cooldown", defaults.getCoolDownTime()));
        logger.accept(nameInLog + infoStringCooldown(false));

        // Warm-Up
        profile.setWarmup(config.getInt("warmup.time", defaults.warmup));
        profile.setWarmupCancelOnMove(config.getBoolean("warmup.cancel-on-move", defaults.isWarmupCancelOnMove()));
        profile.setWarmupCountDown(config.getBoolean("warmup.count-down", defaults.isWarmupCountDown()));
        for (String s : infoStringsWarmup(false)) logger.accept(nameInLog + s);

        // Cost
        profile.setCost(config.getDouble("cost", defaults.getCost()));
        logger.accept(nameInLog + infoStringCost(false));

        // Bounds
        profile.setLowBound(config.getInt("bounds.low", defaults.getLowBound()));
        profile.setHighBound(config.getInt("bounds.high", defaults.getHighBound()));
        logger.accept(nameInLog + infoStringVertBounds(false));

        // Check Radius
        profile.setCheckRadiusXZ(config.getInt("check-radius.x-z", defaults.getCheckRadiusXZ()));
        profile.setCheckRadiusVert(config.getInt("check-radius.vert", defaults.getCheckRadiusVert()));
        logger.accept(nameInLog + infoStringCheckRadius(false));

        // Max Attempts
        profile.setMaxAttempts(config.getInt("max-attempts.value", defaults.getMaxAttempts()));
        logger.accept(nameInLog + infoStringMaxAttempts(false));

        // Prefer sync tp - on command
        profile.setPreferSyncTpOnCommand(
            config.getBoolean("prefer-sync-tp.command", defaults.isPreferSyncTpOnCommand()));
        logger.accept(nameInLog + infoStringPreferSyncTpOnCommand(false));

        // Location Caching
        profile.setCacheLocationCount(config.getInt("preparations.cache-locations", defaults.getCacheLocationCount()));
        logger.accept(nameInLog + infoStringLocationCaching(false));

        // Location check profile
        profile.setCheckProfile(config.getString("location-checking-profile", null) == null
                                    ? defaults.getCheckProfile()
                                    : RtpProfile.LocCheckProfiles.values()[config.getString("location-checking-profile")
                                                                                 .toLowerCase().charAt(0) - 'a']);
        logger.accept(nameInLog + infoStringLocationCheckProfile(false));

        // Commands To Run
        profile.setCommandsToRun(config.getStringList("then-execute").size() == 0
                                     ? defaults.getCommandsToRun()
                                     : config.getStringList("then-execute").toArray(String[]::new));


    }
}
