package biz.donvi.jakesRTP1API.configuration;

import biz.donvi.jakesRTP1API.util.CoolDownTracker;
import org.bukkit.World;

import java.util.List;

public interface RtpProfile {
    /**
     * Gets the name of this RtpProfile. (Note: the name of a setting will never change)
     *
     * @return The name of the RtpProfile.
     */
    String getName();

    String getVisibleName();

    void setVisibleName(String visibleName);


    boolean isCommandEnabled();

    void setCommandEnabled(boolean commandEnabled);

    /**
     * Tells if this RtpProfile requires players to have an explicit permission to use this RtpProfile.
     * In short, if this returns true, the player must have the permission node returned by
     * {@link #requiredExplicitPermission()} to be able to rtp with this profile. If false, then no additional
     * permission check is required.
     *
     * @return True if the player is required to have an extra permission node to use this RtpProfile, false otherwise.
     */
    boolean isRequireExplicitPermission();

    /**
     * Sets the `require-explicit-permission` configuration option.
     * <br> See {@link #isRequireExplicitPermission()} for more details on the option.
     *
     * @param requireExplicitPermission The new value for `require-explicit-permission`
     */
    void setRequireExplicitPermission(boolean requireExplicitPermission);

    void setRequireExplicitPermName(String requireExplicitPermName);

    /**
     * Returns the permission node that would be required if {@link #isRequireExplicitPermission()} returns true.
     *
     * @return The permission node that would be required if {@link #isRequireExplicitPermission()} returns true.
     */
    String requiredExplicitPermission();

    /**
     * Gets the priority of the RtpProfile. Priority is used to determine which profile should be used when there is
     * any ambiguity present; the profile with the highest priority will be used. Ambiguity can arise when players
     * attempt to rtp without specifying a RtpProfile name, as that is allowed both in the `/rtp` and `/force-rtp`
     * command. When this happens, an attempt at selecting an RtpProfile will made based first on the players
     * permissions, and the RtpProfiles' call-from-worlds. If multiple are still present, then the one with the highest
     * priority is picked.
     *
     * @return The priority of this RtpProfile. Higher priorities override lower priorities.
     */
    float getPriority();

    void setPriority(float priority);

    World getLandingWorld();

    void setLandingWorld(World landingWorld);

    List<World> getCallFromWorlds();

    void setCallFromWorlds(List<World> callFromWorlds);

    DistributionProfile getDistribution();

    boolean isDistributionPrimaryOwner();

    void setDistribution(DistributionProfile distribution);

    void setDistribution(DistributionProfile distribution, String primaryOwner);

    float getCoolDownTime();

    void setCoolDownTime(float timeInSeconds);

    boolean isCoolDownPrimaryOwner();

    CoolDownTracker getCoolDown();

    void setCoolDown(CoolDownTracker coolDown);

    void setCoolDown(CoolDownTracker coolDown, String primaryOwner);

    int getWarmup();

    void setWarmup(int warmup);

    boolean isWarmupCancelOnMove();

    void setWarmupCancelOnMove(boolean warmupCancelOnMove);

    boolean isWarmupCountDown();

    void setWarmupCountDown(boolean warmupCountDown);

    int getLowBound();

    void setLowBound(int lowBound);

    int getHighBound();

    void setHighBound(int highBound);

    int getCheckRadiusXZ();

    void setCheckRadiusXZ(int checkRadiusXZ);

    int getCheckRadiusVert();

    void setCheckRadiusVert(int checkRadiusVert);

    int getMaxAttempts();

    void setMaxAttempts(int maxAttempts);

    int getCacheLocationCount();

    void setCacheLocationCount(int cacheLocationCount);

    boolean isPreferSyncTpOnCommand();

    void setPreferSyncTpOnCommand(boolean preferSyncTpOnCommand);

    LocCheckProfiles getCheckProfile();

    void setCheckProfile(LocCheckProfiles checkProfile);

    String[] getCommandsToRun();

    void setCommandsToRun(String[] commandsToRun);

    double getCost();

    void setCost(double cost);

    boolean warmupEnabled();

    boolean canUseLocQueue();

    boolean isValid();

    public enum LocCheckProfiles {AUTO, TOP_DOWN, MIDDLE_OUT}
}
