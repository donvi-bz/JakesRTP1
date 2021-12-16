package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1API.configuration.BlockList;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.util.JrtpBaseException;
import biz.donvi.jakesRTP1_mock.MockServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConfigurationTests {
    static Path             realResources;
    static Path             testResources;
    static RtpProfileLinker rtpProfileLinker;

    @BeforeAll
    static void setup() {
        realResources = Paths.get("src/main/resources");
        testResources = Paths.get("src/test/resources");
        ConfigurationFactoryImpl.buildFactory();
        rtpProfileLinker = new RtpProfileLinker();

    }

    @BeforeAll
    static void makeFakeWorlds() {
        Bukkit.setServer(new MockServer());
    }

    static RtpProfile rtpProfile_default;

    @Test
    @Order(0)
    @DisplayName("Load Internal Default")
    public void loadInternalDefaultCheck() {
        assertEquals("__INTERNAL_DEFAULT_SETTINGS__", RtpProfileLoader.defaultRtpProfile.getName());
    }

    //<editor-fold desc="Default RtpProfile | Test Cases">
    @Test
    @Order(100)
    @DisplayName("Load | default-settings ================")
    void loadDefaultRtpProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = realResources.resolve("rtpProfiles/default-settings.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_default = RtpProfileLoader.loader.load(
            "default-settings",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_default);
    }

    @Test
    @Order(101)
    @DisplayName("Check | default-settings | visible-name")
    void checkDefaultRtpProfile_VisibleName() {
        assertEquals("default-settings", rtpProfile_default.getVisibleName());
    }

    @Test
    @Order(102)
    @DisplayName("Check | default-settings | command-enabled")
    void checkDefaultRtpProfile_CommandEnabled() {
        assertTrue(rtpProfile_default.isCommandEnabled());
    }

    @Test
    @Order(103)
    @DisplayName("Check | default-settings | require-permission")
    void checkDefaultRtpProfile_RequireExplicitPermission() {
        assertFalse(rtpProfile_default.isRequireExplicitPermission());
    }

    @Test
    @Order(104)
    @DisplayName("Check | default-settings | require-permission name")
    void checkDefaultRtpProfile_ExplicitPermissionName() {
        assertEquals("jakesrtp.use.default-settings", rtpProfile_default.requiredExplicitPermission());
    }

    @Test
    @Order(105)
    @DisplayName("Check | default-settings | priority")
    void checkDefaultRtpProfile_Priority() {
        assertEquals(1.0f, rtpProfile_default.getPriority());
    }

    @Test
    @Order(106)
    @DisplayName("Check | default-settings | landing-world")
    void checkDefaultRtpProfile_LandingWorld() {
        assertEquals("world", rtpProfile_default.getLandingWorld().getName());
    }

    @Test
    @Order(107)
    @DisplayName("Check | default-settings | call-from-worlds")
    void checkDefaultRtpProfile_CallFromWorlds() {
        Pattern worldPattern = Pattern.compile("world.*");
        Set<World> worldSet =
            Bukkit.getServer().getWorlds().stream()
                  .filter(world -> worldPattern.matcher(world.getName()).matches())
                  .collect(Collectors.toSet());
        assertEquals(worldSet, new HashSet<>(rtpProfile_default.getCallFromWorlds()));
    }

    @Test
    @Order(108)
    @DisplayName("Check | default-settings | distribution (none given)")
    void checkDefaultRtpProfile_Distribution() {
        assertNotNull(rtpProfile_default.getDistribution());
        assertNotNull(rtpProfile_default.getDistribution().getShape());
        // Skip checks of what is actually in the distribution since those are all defaults in code
    }

    @Test
    @Order(109)
    @DisplayName("Check | default-settings | cool-down")
    void checkDefaultRtpProfile_CoolDown() {
        assertNotNull(rtpProfile_default.getCoolDown());
        assertEquals(30.0f, rtpProfile_default.getCoolDownTime());
    }

    @Test
    @Order(110)
    @DisplayName("Check | default-settings | warmup")
    void checkDefaultRtpProfile_Warmup() {
        assertFalse(rtpProfile_default.warmupEnabled());
        // Skips the next 2 checks because they don't matter
    }

    @Test
    @Order(111)
    @DisplayName("Check | default-settings | then-execute")
    void checkDefaultRtpProfile_ThenExecute() {
        String[] toExecute = rtpProfile_default.getCommandsToRun();
        // Just make sure it exists
        assertTrue(toExecute.length == 1 && toExecute[0].length() > 1);
    }

    @Test
    @Order(112)
    @DisplayName("Check | default-settings | cost")
    void checkDefaultRtpProfile_Cost() {
        assertEquals(0.0, rtpProfile_default.getCost());
    }

    @Test
    @Order(113)
    @DisplayName("Check | default-settings | loc-check-prof")
    void checkDefaultRtpProfile_LocCheckProfile() {
        assertEquals(RtpProfile.LocCheckProfiles.AUTO, rtpProfile_default.getCheckProfile());
    }

    @Test
    @Order(114)
    @DisplayName("check | default-settings | block-list")
    void checkDefaultRtpProfile_BlockList() {
        assertNotNull(rtpProfile_default.getBlockList());
        BlockList bl = rtpProfile_default.getBlockList();
        assertNotSame(BlockListImpl.DEFAULTS, bl);
        assertTrue(rtpProfile_default.isBlockListPrimaryOwner());
        // Make sure this all exists...
        assertNotNull(bl.getSafeToBeIn());
        assertNotNull(bl.getUnsafeToBeOn());
        assertNotNull(bl.getTreeLeaves());
        assertNotNull(bl.getUnsafeBiomes());
        // Some better checks
        assertTrue(bl.getSafeToBeIn().size() > 5);
        assertTrue(bl.getSafeToBeIn().contains(Material.AIR));
        assertTrue(bl.getUnsafeToBeOn().size() > 5);
        assertTrue(bl.getUnsafeToBeOn().contains(Material.LAVA));
        assertTrue(bl.getTreeLeaves().size() > 5);
        assertTrue(bl.getTreeLeaves().contains(Material.OAK_LEAVES));
        assertEquals(0, bl.getUnsafeBiomes().size());

    }
    //</editor-fold>

    static RtpProfile rtpProfile_allChanged;

    //<editor-fold desc="AllChanged RtpProfile | Test Cases">
    @Test
    @Order(200)
    @DisplayName("Load | all-changed ================")
    void loadAllChangedProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = testResources.resolve("rtpProfiles/all-changed.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_allChanged = RtpProfileLoader.loader.load(
            "all-changed",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_allChanged);
    }

    @Test
    @Order(201)
    @DisplayName("Check | all-changed | visible-name")
    void checkAllChangedProfile_VisibleName() {
        assertEquals("potato-changed", rtpProfile_allChanged.getVisibleName());
    }

    @Test
    @Order(202)
    @DisplayName("Check | all-changed | command-enabled")
    void checkAllChangedProfile_CommandEnabled() {
        assertFalse(rtpProfile_allChanged.isCommandEnabled());
    }

    @Test
    @Order(203)
    @DisplayName("Check | all-changed | require-permission")
    void checkAllChangedProfile_RequireExplicitPermission() {
        assertTrue(rtpProfile_allChanged.isRequireExplicitPermission());
    }

    @Test
    @Order(204)
    @DisplayName("Check | all-changed | require-permission name")
    void checkAllChangedProfile_ExplicitPermissionName() {
        assertEquals("jakesrtp.use.all-changed", rtpProfile_allChanged.requiredExplicitPermission());
    }

    @Test
    @Order(205)
    @DisplayName("Check | all-changed | priority")
    void checkAllChangedProfile_Priority() {
        assertEquals(1.1f, rtpProfile_allChanged.getPriority());
    }

    @Test
    @Order(206)
    @DisplayName("Check | all-changed | landing-world")
    void checkAllChangedProfile_LandingWorld() {
        assertEquals("world_nether", rtpProfile_allChanged.getLandingWorld().getName());
    }

    @Test
    @Order(207)
    @DisplayName("Check | all-changed | call-from-worlds")
    void checkAllChangedProfile_CallFromWorlds() {
        Pattern worldPattern = Pattern.compile("(world|world_the_end)");
        Set<World> worldSet =
            Bukkit.getServer().getWorlds().stream()
                  .filter(world -> worldPattern.matcher(world.getName()).matches())
                  .collect(Collectors.toSet());
        assertEquals(worldSet, new HashSet<>(rtpProfile_allChanged.getCallFromWorlds()));
    }

    @Test
    @Order(208)
    @DisplayName("Check | all-changed | distribution (circle given)")
    void checkAllChangedProfile_Distribution() {
        assertNotNull(rtpProfile_allChanged.getDistribution());
        assertNotNull(rtpProfile_allChanged.getDistribution().getShape());
        assertEquals("circle", rtpProfile_allChanged.getDistribution().getShape().shapeName());
        // Not checking the details of the distribution, that's for another set of tests
    }

    @Test
    @Order(209)
    @DisplayName("Check | all-changed | cool-down")
    void checkAllChangedProfile_CoolDown() {
        assertNotNull(rtpProfile_allChanged.getCoolDown());
        assertEquals(37.0f, rtpProfile_allChanged.getCoolDownTime());
    }

    @Test
    @Order(210)
    @DisplayName("Check | all-changed | warmup")
    void checkAllChangedProfile_Warmup() {
        assertTrue(rtpProfile_allChanged.warmupEnabled());
        assertFalse(rtpProfile_allChanged.isWarmupCancelOnMove());
        assertFalse(rtpProfile_allChanged.isWarmupCountDown());
    }

    @Test
    @Order(211)
    @DisplayName("Check | all-changed | then-execute")
    void checkAllChangedProfile_ThenExecute() {
        String[] toExecute = rtpProfile_allChanged.getCommandsToRun();
        // Just make sure it exists
        assertTrue(toExecute.length == 2 && toExecute[0].length() > 1);
    }

    @Test
    @Order(212)
    @DisplayName("Check | all-changed | cost")
    void checkAllChangedProfile_Cost() {
        assertEquals(1.1, rtpProfile_allChanged.getCost());
    }

    @Test
    @Order(213)
    @DisplayName("Check | all-changed | loc-check-prof")
    void checkAllChangedProfile_LocCheckProfile() {
        assertEquals(RtpProfile.LocCheckProfiles.TOP_DOWN, rtpProfile_allChanged.getCheckProfile());
    }

    @Test
    @Order(214)
    @DisplayName("check | all-changed | block-list")
    void checkAllChangedProfile_BlockList() {
        assertNotNull(rtpProfile_allChanged.getBlockList());
        BlockList bl = rtpProfile_allChanged.getBlockList();
        assertTrue(rtpProfile_allChanged.isBlockListPrimaryOwner());
        // Make sure this all exists...
        assertNotNull(bl.getSafeToBeIn());
        assertNotNull(bl.getUnsafeToBeOn());
        assertNotNull(bl.getTreeLeaves());
        // Some better checks
        assertEquals(1, bl.getSafeToBeIn().size());
        assertTrue(bl.getSafeToBeIn().contains(Material.SNOW));
        assertEquals(1, bl.getUnsafeToBeOn().size());
        assertTrue(bl.getUnsafeToBeOn().contains(Material.MAGMA_BLOCK));
        assertEquals(1, bl.getTreeLeaves().size());
        assertTrue(bl.getTreeLeaves().contains(Material.ACACIA_LEAVES));
        assertEquals(1, bl.getUnsafeBiomes().size());
        assertTrue(bl.getUnsafeBiomes().contains(Biome.OCEAN));

    }
    //</editor-fold>

    static RtpProfile rtpProfile_linkNew;
    static RtpProfile rtpProfile_mrBoring;

    //<editor-fold desc="LinkNew RtpProfile | Test Cases">
    @Test
    @Order(300)
    @DisplayName("Load | link-new ================")
    void loadLinkNewProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = testResources.resolve("rtpProfiles/link-new.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_linkNew = RtpProfileLoader.loader.load(
            "link-new",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_linkNew);
    }

    @Test
    @Order(400)
    @DisplayName("Load | mr-boring ================")
    void loadMrBoringProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = testResources.resolve("rtpProfiles/mr-boring.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_mrBoring = RtpProfileLoader.loader.load(
            "mr-boring",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_linkNew);
    }

    @Test
    @Order(304)
    @DisplayName("Check | link-new | require-permission name")
    void checkLinkNewProfile_ExplicitPermissionName() {
        // Why does this exist here you ask? Cause `linkNew` happens to be where we test every other setting that can be
        // either some arbitrary value or some string. This is where we do the string test for an otherwise boolean val
        assertEquals("jakesrtp.use.special-perm", rtpProfile_linkNew.requiredExplicitPermission());
    }

    @Test
    @Order(508)
    @DisplayName("Check | link-new | distribution (from mrBoring)")
    void checkLinkNewProfile_Distribution() {
        // Make sure they have the same distribution
        assertSame(rtpProfile_linkNew.getDistribution(), rtpProfile_mrBoring.getDistribution());
        // But also make sure we don't own the distribution
        assertFalse(rtpProfile_linkNew.isDistributionPrimaryOwner());
    }

    @Test
    @Order(509)
    @DisplayName("Check | link-new | cool-down (from mrBoring)")
    void checkLinkNewProfile_CoolDown() {
        // Make sure they have the same cooldown
        assertSame(rtpProfile_linkNew.getCoolDown(), rtpProfile_mrBoring.getCoolDown());
        // But also make sure we don't own the cooldown
        assertFalse(rtpProfile_linkNew.isCoolDownPrimaryOwner());
    }

    @Test
    @Order(514)
    @DisplayName("check | link-new | block-list (from mrBoring)")
    void checkLinkNewProfile_BlockList() {
        assertSame(rtpProfile_mrBoring.getBlockList(), rtpProfile_linkNew.getBlockList());
        assertFalse(rtpProfile_linkNew.isBlockListPrimaryOwner());
    }
    //</editor-fold>

    static RtpProfile rtpProfile_linkOld;

    //<editor-fold desc="LinkOld RtpProfile | Test Cases">
    @Test
    @Order(600)
    @DisplayName("Load | link-old ================")
    void loadLinkOldProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = testResources.resolve("rtpProfiles/link-old.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_linkOld = RtpProfileLoader.loader.load(
            "link-old",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_linkOld);
    }

    @Test
    @Order(608)
    @DisplayName("Check | link-old | distribution (from allChanged)")
    void checkLinkOldProfile_Distribution() {
        // Make sure they have the same distribution
        assertSame(rtpProfile_allChanged.getDistribution(), rtpProfile_linkOld.getDistribution());
        // But also make sure we don't own the distribution
        assertFalse(rtpProfile_linkOld.isDistributionPrimaryOwner());
    }

    @Test
    @Order(609)
    @DisplayName("Check | link-old | cool-down (from allChanged)")
    void checkLinkOldProfile_CoolDown() {
        // Make sure they have the same cooldown
        assertSame(rtpProfile_allChanged.getCoolDown(), rtpProfile_linkOld.getCoolDown());
        // But also make sure we don't own the cooldown
        assertFalse(rtpProfile_linkOld.isCoolDownPrimaryOwner());
    }


    @Test
    @Order(614)
    @DisplayName("check | link-old | block-list (from allChanged)")
    void checkLinkOldProfile_BlockList() {
        assertSame(rtpProfile_allChanged.getBlockList(), rtpProfile_linkOld.getBlockList());
        assertFalse(rtpProfile_linkOld.isBlockListPrimaryOwner());
    }
    //</editor-fold>
    
    static RtpProfile rtpProfile_v0;

    @Test
    @Order(700)
    @DisplayName("Load | default-settings-v0 ================")
    void loadV0RtpProfile() throws JrtpBaseException.ConfigurationException {
        File cfgFile = testResources.resolve("rtpProfiles_v0/default-settings-v0.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        assertTrue(cfgFile.exists());
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

        rtpProfile_v0 = RtpProfileLoader.loader.load(
            "default-settings-v0",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

        assertNotNull(rtpProfile_v0);
    }
}
