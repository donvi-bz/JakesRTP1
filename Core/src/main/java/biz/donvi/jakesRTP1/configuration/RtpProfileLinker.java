package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1.util.GeneralUtil.Pair;
import biz.donvi.jakesRTP1API.configuration.BlockList;
import biz.donvi.jakesRTP1API.configuration.DistributionProfile;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.util.CoolDownTracker;
import org.apache.commons.collections4.map.ReferenceMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.HARD;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.WEAK;

public class RtpProfileLinker {
    private final Map<String, RtpProfile>          rtpProfileByName          = new ReferenceMap<>(HARD, WEAK);
    private final Map<String, DistributionProfile> distributionProfileByName = new ReferenceMap<>(HARD, WEAK);
    private final Map<String, CoolDownTracker>     coolDownTrackerByName     = new ReferenceMap<>(HARD, WEAK);
    private final Map<String, BlockList>           blockListByName           = new ReferenceMap<>(HARD, WEAK);

    private final ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingDistribution    = new ArrayList<>();
    private final ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingCoolDownTracker = new ArrayList<>();
    private final ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingBlockList       = new ArrayList<>();


    public void registerProfile(RtpProfile profile) {
        String name = profile.getName();
        // Add this profile to the maps so other profiles can use it
        rtpProfileByName.put(name, profile);
        if (profile.getDistribution() != null) forceRegisterDistribution(name, profile.getDistribution());
        if (profile.getCoolDown() != null) forceRegisterCoolDown(name, profile.getCoolDown());
        if (profile.getBlockList() != null) forceRegisterBlockList(name, profile.getBlockList());
    }

    private <T> void forceRegisterT(
        String name, T setting,
        Map<String, T> storeMap, ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingList,
        TriConsumer<RtpProfile, T, String> setMethod
    ) {
        Objects.requireNonNull(setting);
        storeMap.put(name, setting);
        ArrayList<Pair<String, WeakReference<RtpProfile>>> itemsToRemove = new ArrayList<>();
        for (var pair : awaitingList) {
            RtpProfile waitingProfile;
            if (pair.key.equalsIgnoreCase(name) && (waitingProfile = pair.value.get()) != null) {
                setMethod.accept(waitingProfile, setting, name);
                itemsToRemove.add(pair);
            }
        }
        awaitingList.removeAll(itemsToRemove);
    }

    void forceRegisterDistribution(String distName, DistributionProfile dist) {
        forceRegisterT(
            distName, dist,
            distributionProfileByName, awaitingDistribution,
            RtpProfile::setDistribution
        );
    }

    void forceRegisterCoolDown(String coolName, CoolDownTracker coolDown) {
        forceRegisterT(
            coolName, coolDown,
            coolDownTrackerByName, awaitingCoolDownTracker,
            RtpProfile::setCoolDown
        );
    }

    void forceRegisterBlockList(String coolName, BlockList blockList) {
        forceRegisterT(
            coolName, blockList,
            blockListByName, awaitingBlockList,
            RtpProfile::setBlockList
        );
    }

    private <T> void linkT(
        RtpProfile profile, String innerSettingName,
        Map<String, T> storeMap, ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingList,
        TriConsumer<RtpProfile, T, String> setMethod
    ) {
        T innerSetting = storeMap.get(innerSettingName);
        if (innerSetting != null) {
            setMethod.accept(profile, innerSetting, innerSettingName);
            storeMap.put(profile.getName(), innerSetting);
        } else awaitingList.add(new Pair<>(innerSettingName, new WeakReference<RtpProfile>(profile)));
    }

    public void linkDistribution(RtpProfile profile, String distName) {
        linkT(
            profile, distName,
            distributionProfileByName, awaitingDistribution,
            RtpProfile::setDistribution
        );
    }

    public void linkCoolDown(RtpProfile profile, String coolName) {
        linkT(
            profile, coolName,
            coolDownTrackerByName, awaitingCoolDownTracker,
            RtpProfile::setCoolDown
        );
    }

    public void linkBlockList(RtpProfile profile, String blockListName) {
        linkT(
            profile, blockListName,
            blockListByName, awaitingBlockList,
            RtpProfile::setBlockList
        );
    }

    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }
}
