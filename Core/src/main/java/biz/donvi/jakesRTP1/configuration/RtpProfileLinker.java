package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1.util.GeneralUtil.Pair;
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

    private final ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingDistribution    = new ArrayList<>();
    private final ArrayList<Pair<String, WeakReference<RtpProfile>>> awaitingCoolDownTracker = new ArrayList<>();


    public void registerProfile(RtpProfile profile) {
        String name = profile.getName();
        // Add this profile to the maps so other profiles can use it
        rtpProfileByName.put(name, profile);
        if (profile.getDistribution() != null) forceRegisterDistribution(name, profile.getDistribution());
        if (profile.getCoolDown() != null) forceRegisterCoolDown(name, profile.getCoolDown());
    }

    void forceRegisterDistribution(String distName, DistributionProfile dist) {
        Objects.requireNonNull(dist);
        distributionProfileByName.put(distName, dist);
        @SuppressWarnings("rawtypes")
        ArrayList<Pair> itemsToRemove = new ArrayList<>();
        for (var pair : awaitingDistribution) {
            RtpProfile waitingProfile;
            if (pair.key.equalsIgnoreCase(distName) && (waitingProfile = pair.value.get()) != null) {
                waitingProfile.setDistribution(dist, distName);
                itemsToRemove.add(pair);
            }
        }
        awaitingDistribution.removeAll(itemsToRemove);
    }

    void forceRegisterCoolDown(String coolName, CoolDownTracker coolDown) {
        Objects.requireNonNull(coolDown);
        coolDownTrackerByName.put(coolName, coolDown);
        @SuppressWarnings("rawtypes")
        ArrayList<Pair> itemsToRemove = new ArrayList<>();
        for (var pair : awaitingCoolDownTracker) {
            RtpProfile waitingProfile;
            if (pair.key.equalsIgnoreCase(coolName) && (waitingProfile = pair.value.get()) != null) {
                waitingProfile.setCoolDown(coolDown, coolName);
                itemsToRemove.add(pair);
            }
        }
        awaitingCoolDownTracker.removeAll(itemsToRemove);
    }

    public void linkDistribution(RtpProfile profile, String distName) {
        DistributionProfile dist = distributionProfileByName.get(distName);
        if (dist != null) {
            profile.setDistribution(dist, distName);
            distributionProfileByName.put(profile.getName(), dist);
        } else awaitingDistribution.add(new Pair<>(distName, new WeakReference<RtpProfile>(profile)));
    }

    public void linkCoolDown(RtpProfile profile, String coolName) {
        CoolDownTracker cool = coolDownTrackerByName.get(coolName);
        if (cool != null) {
            profile.setCoolDown(cool, coolName);
            coolDownTrackerByName.put(profile.getName(), cool);
        } else awaitingCoolDownTracker.add(new Pair<>(coolName, new WeakReference<RtpProfile>(profile)));
    }
}
