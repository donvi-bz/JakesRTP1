package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1API.configuration.BlockList;
import biz.donvi.jakesRTP1API.configuration.ConfigurationFactory;
import biz.donvi.jakesRTP1API.configuration.DistributionProfile;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.util.CoolDownTracker;
import biz.donvi.jakesRTP1API.util.DebugPrintable;
import biz.donvi.jakesRTP1API.util.MultiDebugPrintProvider;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.List;

public class RtpProfileImpl implements RtpProfile, MultiDebugPrintProvider, DebugPrintable {

    final static String EXPLICIT_PERM_PREFIX = "jakesrtp.use.";

    // Actual properties of the RtpSettings
    protected final String name;

    protected String              visibleName;
    protected boolean             commandEnabled            = true;
    protected boolean             requireExplicitPermission = false;
    protected String              requireExplicitPermName   = null;
    protected float               priority                  = 1;
    protected World               landingWorld              = null;
    protected List<World>         callFromWorlds            = Collections.emptyList();
    protected String              distribution_from         = null;
    protected DistributionProfile distribution              = null;
    protected String              coolDown_from             = null;
    protected CoolDownTracker     coolDown                  = null;
    protected int                 warmup                    = 0;
    protected boolean             warmupCancelOnMove        = true;
    protected boolean             warmupCountDown           = true;
    protected String              blockList_from            = null;
    protected BlockList           blockList                 = BlockListImpl.DEFAULTS;
    protected int                 lowBound                  = 32;
    protected int                 highBound                 = 255;
    protected int                 checkRadiusXZ             = 2;
    protected int                 checkRadiusVert           = 2;
    protected int                 maxAttempts               = 10;
    protected int                 cacheLocationCount        = 10;
    protected boolean             preferSyncTpOnCommand     = false;
    protected LocCheckProfiles    checkProfile              = LocCheckProfiles.AUTO;
    protected String[]            commandsToRun             = new String[0];
    protected double              cost                      = 0;

    // For internal use
    final int[] configVersion;
    FileConfiguration verbatimConfig;

    // Now on to the constructors

    public RtpProfileImpl(String name) {
        this(name, new int[]{-1});
    }

    RtpProfileImpl(String name, int[] configVersion) {
        this.name = this.visibleName = name;
        this.configVersion = configVersion;
        this.distribution = ConfigurationFactory.newDistributionProfile("square");
    }

    //<editor-fold desc="Property Getters & Setters">

    //// Property:  name

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVisibleName() {
        return visibleName;
    }

    @Override
    public void setVisibleName(String visibleName) {
        if (visibleName == null || visibleName.equals(""))
            this.visibleName = this.name;
        else this.visibleName = visibleName;
    }

    //// Property:  commandEnabled

    @Override
    public boolean isCommandEnabled() {
        return commandEnabled;
    }

    @Override
    public void setCommandEnabled(boolean commandEnabled) {
        this.commandEnabled = commandEnabled;
    }

    //// Property:  requireExplicitPermission

    @Override
    public boolean isRequireExplicitPermission() {
        return requireExplicitPermission;
    }

    @Override
    public void setRequireExplicitPermission(boolean requireExplicitPermission) {
        this.requireExplicitPermission = requireExplicitPermission;
    }

    @Override
    public void setRequireExplicitPermName(String requireExplicitPermName) {
        this.requireExplicitPermName = requireExplicitPermName;
    }

    @Override
    public String requiredExplicitPermission() {
        return EXPLICIT_PERM_PREFIX + (requireExplicitPermName != null
            ? requireExplicitPermName
            : name);
    }
    //// Property:  priority

    @Override
    public float getPriority() {
        return priority;
    }

    @Override
    public void setPriority(float priority) {
        this.priority = priority;
    }

    //// Property:  landingWorld

    @Override
    public World getLandingWorld() {
        return landingWorld;
    }

    @Override
    public void setLandingWorld(World landingWorld) {
        this.landingWorld = landingWorld;
    }

    //// Property:  callFromWorlds

    @Override
    public List<World> getCallFromWorlds() {
        return callFromWorlds;
    }

    @Override
    public void setCallFromWorlds(List<World> callFromWorlds) {
        this.callFromWorlds = Collections.unmodifiableList(callFromWorlds);
    }

    //// Property:  distribution

    @Override
    public DistributionProfile getDistribution() {
        return distribution;
    }

    @Override
    public boolean isDistributionPrimaryOwner() {
        return distribution != null && distribution_from != null && distribution_from.equals(name);
    }

    @Override
    public void setDistribution(DistributionProfile distribution) {
        setDistribution(distribution, name);
    }

    @Override
    public void setDistribution(DistributionProfile distribution, String primaryOwner) {
        if (primaryOwner == null) primaryOwner = name;
        this.distribution_from = primaryOwner;
        this.distribution = distribution;
    }

    //// Property:  coolDown

    @Override
    public float getCoolDownTime() {
        if (coolDown == null) return -1;
        else return coolDown.getCoolDownTime();
    }

    @Override
    public void setCoolDownTime(float timeInSeconds) {
        if (this.coolDown == null) setCoolDown(ConfigurationFactory.newCoolDownTracker(timeInSeconds));
        else coolDown.setCoolDownTime(timeInSeconds);
    }

    @Override
    public boolean isCoolDownPrimaryOwner() {
        return coolDown != null && coolDown_from != null && coolDown_from.equals(name);
    }

    @Override
    public CoolDownTracker getCoolDown() {
        return coolDown;
    }

    @Override
    public void setCoolDown(CoolDownTracker coolDown) {
        setCoolDown(coolDown, name);
    }

    @Override
    public void setCoolDown(CoolDownTracker coolDown, String primaryOwner) {
        if (primaryOwner == null) primaryOwner = name;
        this.coolDown_from = primaryOwner;
        this.coolDown = coolDown;
    }

    //// Property:  warmup

    @Override
    public int getWarmup() {
        return warmup;
    }

    @Override
    public void setWarmup(int warmup) {
        this.warmup = warmup;
    }

    //// Property:  warmupCancelOnMove

    @Override
    public boolean isWarmupCancelOnMove() {
        return warmupCancelOnMove;
    }

    @Override
    public void setWarmupCancelOnMove(boolean warmupCancelOnMove) {
        this.warmupCancelOnMove = warmupCancelOnMove;
    }

    //// Property:  warmupCountDown

    @Override
    public boolean isWarmupCountDown() {
        return warmupCountDown;
    }

    @Override
    public void setWarmupCountDown(boolean warmupCountDown) {
        this.warmupCountDown = warmupCountDown;
    }

    //// Property:  blockList

    @Override
    public BlockList getBlockList() {
        return blockList;
    }

    @Override
    public boolean isBlockListPrimaryOwner() {
        return blockList_from != null && blockList != null && blockList_from.equals(name);
    }

    @Override
    public void setBlockList(BlockList blockList) {
        setBlockList(blockList, name);
    }

    @Override
    public void setBlockList(BlockList blockList, String primaryOwner) {
        if (primaryOwner == null) primaryOwner = name;
        this.blockList_from = primaryOwner;
        this.blockList = blockList;
    }

    //// Property:  lowBound

    @Override
    public int getLowBound() {
        return lowBound;
    }

    @Override
    public void setLowBound(int lowBound) {
        this.lowBound = lowBound;
    }

    //// Property:  highBound

    @Override
    public int getHighBound() {
        return highBound;
    }

    @Override
    public void setHighBound(int highBound) {
        this.highBound = highBound;
    }

    //// Property:  checkRadiusXZ

    @Override
    public int getCheckRadiusXZ() {
        return checkRadiusXZ;
    }

    @Override
    public void setCheckRadiusXZ(int checkRadiusXZ) {
        this.checkRadiusXZ = checkRadiusXZ;
    }

    //// Property:  checkRadiusVert

    @Override
    public int getCheckRadiusVert() {
        return checkRadiusVert;
    }

    @Override
    public void setCheckRadiusVert(int checkRadiusVert) {
        this.checkRadiusVert = checkRadiusVert;
    }

    //// Property:  maxAttempts

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    //// Property:  cacheLocationCount

    @Override
    public int getCacheLocationCount() {
        return cacheLocationCount;
    }

    @Override
    public void setCacheLocationCount(int cacheLocationCount) {
        this.cacheLocationCount = cacheLocationCount;
    }


    @Override
    public boolean isPreferSyncTpOnCommand() {
        return preferSyncTpOnCommand;
    }

    @Override
    public void setPreferSyncTpOnCommand(boolean preferSyncTpOnCommand) {
        this.preferSyncTpOnCommand = preferSyncTpOnCommand;
    }

    @Override
    public LocCheckProfiles getCheckProfile() {
        return checkProfile;
    }

    @Override
    public void setCheckProfile(LocCheckProfiles checkProfile) {
        this.checkProfile = checkProfile;
    }

    @Override
    public String[] getCommandsToRun() {
        return commandsToRun;
    }

    @Override
    public void setCommandsToRun(String[] commandsToRun) {
        this.commandsToRun = commandsToRun;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
//        this.cost = plugin.canUseEconomy() ? cost : 0; // TODO BRING THIS BACK
    }
    //</editor-fold>

    //<editor-fold desc="Other Getters">
    @Override
    public boolean warmupEnabled() {
        return warmup > 0;
    }

    @Override
    public boolean canUseLocQueue() {
        return distribution != null &&
               distribution.getCenter() != DistributionProfile.CenterTypes.PLAYER_LOCATION &&
               cacheLocationCount > 0;
    }

    @Override
    public boolean isValid() {
        return landingWorld != null &&
               callFromWorlds != null &&
               distribution != null &&
               coolDown != null;
    }
    //</editor-fold>

    //<editor-fold desc="MultiDebugPrintProvider">
    @Override
    public List<String> getDebugPrinterNames() {
        return Collections.singletonList("default");
    }

    @Override
    public DebugPrintable getDebugPrinterFor(String name) {
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="DebugPrintable">
    @Override
    public String getDebugPrintableName() {
        return "default";
    }

    @Override
    public List<String> infoStrings() {
        throw new Error("not done yet");
    }
    //</editor-fold>


}
