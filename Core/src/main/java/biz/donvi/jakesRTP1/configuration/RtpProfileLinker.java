package biz.donvi.jakesRTP1.configuration;

import biz.donvi.JakesRTP1.configuration.DistributionProfile;
import biz.donvi.JakesRTP1.configuration.RtpProfile;
import biz.donvi.JakesRTP1.util.CoolDownTracker;
import biz.donvi.jakesRTP1.util.GeneralUtil;
import biz.donvi.jakesRTP1.util.GeneralUtil.Pair;
import org.apache.commons.collections4.map.ReferenceMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

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
        distributionProfileByName.put(name, profile.getDistribution());
        coolDownTrackerByName.put(name, profile.getCoolDown());
        // See if this profile is a match for any of the profiles on a wait list. Got to do this for both lists.
        @SuppressWarnings("rawtypes") // Meh. It's easier this way.
        ArrayList<Pair> itemsToRemove = new ArrayList<>();
        for (var pair : awaitingDistribution) {
            RtpProfile waitingProfile;
            if (pair.key.equalsIgnoreCase(name) && (waitingProfile = pair.value.get()) != null) {
                waitingProfile.setDistribution(profile.getDistribution());
                itemsToRemove.add(pair);
            }
        }
        awaitingDistribution.removeAll(itemsToRemove);
        itemsToRemove.clear();
        for (var pair : awaitingCoolDownTracker) {
            RtpProfile waitingProfile;
            if (pair.key.equalsIgnoreCase(name) && (waitingProfile = pair.value.get()) != null) {
                waitingProfile.setCoolDown(profile.getCoolDown());
                itemsToRemove.add(pair);
            }
        }
        awaitingCoolDownTracker.removeAll(itemsToRemove);
    }

    public void linkDistribution(RtpProfile profile, String distName) {
        DistributionProfile dist = distributionProfileByName.get(distName);
        if (dist != null) profile.setDistribution(dist);
        else awaitingDistribution.add(new Pair<>(distName, new WeakReference<RtpProfile>(profile)));
    }

    public void linkCoolDown(RtpProfile profile, String coolName) {
        CoolDownTracker cool = coolDownTrackerByName.get(coolName);
        if (cool != null) profile.setCoolDown(cool);
        else awaitingCoolDownTracker.add(new Pair<>(coolName, new WeakReference<RtpProfile>(profile)));
    }
}
