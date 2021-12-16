package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1.configuration.distributions.DistributionLoader;
import biz.donvi.jakesRTP1.util.GeneralUtil;
import biz.donvi.jakesRTP1API.configuration.BlockList;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.util.JrtpBaseException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RtpProfileLoader {


    public static final RtpProfileLoader loader = new RtpProfileLoader();

    static final RtpProfile defaultRtpProfile;

    private static final Map<String, Biome> BIOME_MAP = Arrays
        .stream(Biome.values()).collect(Collectors.toMap(Enum::name, Function.identity()));

    static {
        RtpProfile defaultProfile;
        try {
            defaultProfile = loader.load(
                "__INTERNAL_DEFAULT_SETTINGS__",
                YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(
                    RtpProfileLoader.class.getClassLoader().getResourceAsStream(
                        "rtpProfiles/default-settings.yml")))),
                new RtpProfileImpl("__EMPTY__"),
                new RtpProfileLinker(),
                nop -> {}
            );
        } catch (Exception e) {
            e.printStackTrace();
            defaultProfile = new RtpProfileImpl("__INTERNAL_EMPTY__");
        }
        defaultRtpProfile = defaultProfile;
    }

    private RtpProfileLoader() {}


    public RtpProfile load(
        String name, FileConfiguration config, RtpProfile defaults, RtpProfileLinker linker, Consumer<String> logger
    )
    throws JrtpBaseException.ConfigurationException {
        if (defaults == null) defaults = defaultRtpProfile;

        final String nameInLog = "[" + name + "] ";
        logger.accept("Loading rtpSettings...");

        // config version check
        final int[] configVersion = Arrays.stream(config.getString("$version", "0").split("\\."))
                                          .mapToInt(Integer::parseInt).toArray();

        final RtpProfileImpl profile = new RtpProfileImpl(name, configVersion);
        profile.verbatimConfig = config;

        // ======== Command Enabled ========
        profile.setCommandEnabled(config.getBoolean("command-enabled", defaults.isCommandEnabled()));
        logger.accept(nameInLog + "Command: " + (profile.isCommandEnabled() ? "Enabled" : "Disabled"));

        // ======== Visible Name ========
        profile.setVisibleName(config.getString("visible-name", null));
        logger.accept(nameInLog + "Visible name: '" + profile.getVisibleName() + "'");

        // ======== Require Explicit Permission ========
        if (config.contains("require-explicit-permission", true))
            profile.setRequireExplicitPermission(config.getBoolean(
                "require-explicit-permission", defaults.isRequireExplicitPermission()));
        else {// ↑ Code for v0 config ↑ | ↓ Code for v1 config ↓ | Both included for maximum error protection
            Boolean rsp = config.getObject("require-permission", Boolean.class);
            String rName = config.getString("require-permission");
            profile.setRequireExplicitPermission(rsp == null || rsp);
            if (rsp == null) profile.setRequireExplicitPermName(rName);
        }

        logger.accept(nameInLog + "Require explicit permission node: " + (
            profile.isRequireExplicitPermission()
                ? "True (" + RtpProfileImpl.EXPLICIT_PERM_PREFIX + name + ")"
                : "False"
        ));

        // ======== Priority ========
        profile.setPriority((float) config.getDouble("priority", defaults.getPriority()));
        logger.accept(nameInLog + "Priority: " + profile.getPriority());

        // ======== Landing World ========
        @SuppressWarnings("ConstantConditions") // It's guaranteed to be safe
        World landingWorld = config.getString("landing-world", null) == null ? null
            : Bukkit.getServer().getWorld(config.getString("landing-world", null));
        if (landingWorld == null) landingWorld = defaults.getLandingWorld();
        if (landingWorld == null) throw new JrtpBaseException.ConfigurationException("Landing world not recognised.");
        profile.setLandingWorld(landingWorld);
        logger.accept(nameInLog + "The user will land in the following world: " + landingWorld.getName());

        // ======== Call From Worlds ========
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

        // ======== Distribution v1 ========
        boolean distIsNull = config.getObject("distribution", Object.class) == null;
        String distAsName = config.getString("distribution", null);
        ConfigurationSection distAsConfig = config.getConfigurationSection("distribution");
        String distLoadFashion;

        // If everything is null, then we run with the default value
        if (distIsNull && distAsName == null && distAsConfig == null) {
            profile.setDistribution(defaults.getDistribution());
            distLoadFashion = "provided defaults";
        }
        // If we have a config section, we pass the loading on to the next section.
        else if (distAsConfig != null) {
            profile.setDistribution(DistributionLoader.INSTANCE.loadDistributionProfile(distAsConfig));
            distLoadFashion = "config section";
        }
        // If we have a name, then we link and move on
        else if (distAsName != null) {
            linker.linkDistribution(profile, distAsName);
            distLoadFashion = "linking by name (" + distAsName + ")";
        } else throw new JrtpBaseException.ConfigurationException("Distribution isn't null but also isn't useful");

        logger.accept(nameInLog + "Loaded distribution via " + distLoadFashion); // double log?!

        // ======== Cool-Down ========
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

        if (coolDownIsBorrowed) logger.accept(nameInLog + "Cool-down is shared with settings `" +
                                              (coolDownLit != null ? coolDownLit : defaults.getName()) + "`.");
        String coolDownAsWords = coolDownIsBorrowed && coolDownLit != null && profile.getCoolDown() == null
            ? "[waiting-for-linking-step]" : String.valueOf(profile.getCoolDownTime());
        logger.accept(nameInLog + "Cool-down time: " + coolDownAsWords + " seconds.");

        // ======== Warm-Up ========
        profile.setWarmup(config.getInt("warmup.time", defaults.getWarmup()));
        profile.setWarmupCancelOnMove(config.getBoolean("warmup.cancel-on-move", defaults.isWarmupCancelOnMove()));
        profile.setWarmupCountDown(config.getBoolean("warmup.count-down", defaults.isWarmupCountDown()));
        if (profile.getWarmup() > 0) {
            logger.accept(nameInLog + "Warmup Enabled | Warmup time: " + profile.getWarmup());
            logger.accept(nameInLog + "Cancel warmup on player move: " + profile.isWarmupCancelOnMove());
            logger.accept(nameInLog + "Count down to teleport: " + profile.isWarmupCountDown());
        } else logger.accept(nameInLog + "Warmup Disabled.");

        // ======== Cost ========
        profile.setCost(config.getDouble("cost", defaults.getCost()));
        if (profile.getCost() > 0) logger.accept(nameInLog + "Cost: " + profile.getCost());
        else logger.accept(nameInLog + "Cost disabled.");

        // Block Lists
        boolean blockListIsNull = config.getObject("block-lists", Object.class) == null;
        String blockListAsName = config.getString("block-lists", null);
        ConfigurationSection blockListAsConfig = config.getConfigurationSection("block-lists");
        String blockListLoadFashion;

        // If we have NOTHING where the block list should be, just give it the default
        if (blockListIsNull && blockListAsName == null && blockListAsConfig == null) {
            profile.setBlockList(defaults.getBlockList());
            blockListLoadFashion = "provided defaults";
        }
        // This assumes we have more config options!
        else if (blockListAsConfig != null) {
            BlockListImpl blockList = new BlockListImpl();
            blockList.setSafeToBeIn(matSetFromStringList(blockListAsConfig.getStringList("safe-to-be-in")));
            blockList.setUnsafeToBeOn(matSetFromStringList(blockListAsConfig.getStringList("unsafe-to-be-on")));
            blockList.setTreeLeaves(matSetFromStringList(blockListAsConfig.getStringList("tree-leaves")));
            EnumSet<Biome> badBiomeSet = EnumSet.noneOf(Biome.class);
            blockListAsConfig.getStringList("unsafe-biomes").stream()
                             .map(String::toUpperCase).map(BIOME_MAP::get)
                             .filter(Objects::nonNull).forEach(badBiomeSet::add);
            blockList.setUnsafeBiomes(badBiomeSet);
            profile.setBlockList(blockList);
            blockListLoadFashion = "config section";
        }
        // If we have a name where the block list should be, link it
        else if (blockListAsName != null) {
            linker.linkBlockList(profile, blockListAsName);
            blockListLoadFashion = "linking by name (" + distAsName + ")";
        } else throw new JrtpBaseException.ConfigurationException("Block list isn't null but also isn't useful");

        logger.accept(nameInLog + "Loaded block list via " + blockListLoadFashion);

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


        // Teensy bit of stupid-ness, if loading from version 0, we must make sure our cool-down is owned. To do this,
        // we just re-assign it. The coolDown MUST be linked by this point though!!!
        if (configVersion.length >= 1 && configVersion[0] == 0) {
            profile.setDistribution(profile.getDistribution());
            profile.setCoolDown(profile.getCoolDown());
        }
        // Very important that we register the profile!!!
        linker.registerProfile(profile);
        logger.accept(nameInLog + "Registering profile. (Loading complete)");
        return profile;
    }

    private static EnumSet<Material> matSetFromStringList(List<String> names) {
        return EnumSet.copyOf(
            names.stream()
                 .map(Material::matchMaterial)
                 .filter(Objects::nonNull)
                 .collect(Collectors.toList())
        );
    }

}
