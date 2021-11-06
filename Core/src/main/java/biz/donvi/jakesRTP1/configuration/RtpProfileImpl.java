package biz.donvi.jakesRTP1.configuration;

import biz.donvi.JakesRTP1.util.DebugPrintable;
import biz.donvi.JakesRTP1.util.MultiDebugPrintProvider;
import biz.donvi.JakesRTP1.configuration.ConfigurationFactory;
import biz.donvi.JakesRTP1.configuration.DistributionProfile;
import biz.donvi.JakesRTP1.configuration.RtpProfile;
import biz.donvi.JakesRTP1.util.CoolDownTracker;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.World;

import java.util.Collections;
import java.util.List;

import static biz.donvi.jakesRTP1.util.MessageStyles.*;
import static biz.donvi.jakesRTP1.util.MessageStyles.DebugDisplayLines.*;

@SuppressWarnings("unused")
public class RtpProfileImpl implements RtpProfile, MultiDebugPrintProvider, DebugPrintable {

    final static String EXPLICIT_PERM_PREFIX = "jakesrtp.use.";

    // Actual properties of the RtpSettings
    protected final String name;

    protected boolean             commandEnabled            = true;
    protected boolean             requireExplicitPermission = false;
    protected float               priority                  = 1;
    protected World               landingWorld              = null;
    protected List<World>         callFromWorlds            = Collections.emptyList();
    protected DistributionProfile distribution              = null;
    protected CoolDownTracker     coolDown                  = null;
    protected int                 warmup                    = 0;
    protected boolean             warmupCancelOnMove        = true;
    protected boolean             warmupCountDown           = true;
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

    public RtpProfileImpl(String name) {
        this.name = name;
    }

    //<editor-fold desc="Property Getters & Setters">

    //// Property:  name

    @Override
    public String getName() {
        return name;
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

    public String infoStringCommandEnabled(boolean mcFormat) {
        return LVL_01_SET.format(mcFormat, "Command", enabledOrDisabled(commandEnabled));
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
    public String requiredExplicitPermission() {
        return EXPLICIT_PERM_PREFIX + name;
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
    public void setDistribution(DistributionProfile distribution) {
        this.distribution = distribution;
    }

    //// Property:  coolDown

    @Override
    public float getCoolDownTime() {
        return coolDown.getCoolDownTime();
    }

    @Override
    public void setCoolDownTime(float timeInSeconds) {
        if (this.coolDown == null) this.coolDown = ConfigurationFactory.newCoolDownTracker(timeInSeconds);
        else coolDown.setCoolDownTime(timeInSeconds);
    }

    @Override
    public CoolDownTracker getCoolDown() {
        return coolDown;
    }

    @Override
    public void setCoolDown(CoolDownTracker coolDown) {
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
        this.cost = plugin.canUseEconomy() ? cost : 0;
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
        throw new NotImplementedException();
    }
    //</editor-fold>


}
