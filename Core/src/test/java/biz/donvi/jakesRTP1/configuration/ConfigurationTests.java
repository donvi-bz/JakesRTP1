package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1API.configuration.ConfigurationFactory;
import biz.donvi.jakesRTP1API.configuration.RtpProfile;
import biz.donvi.jakesRTP1API.configuration.distributions.Distribution;
import biz.donvi.jakesRTP1API.util.JrtpBaseException;
import biz.donvi.jakesRTP1_mock.MockServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationTests {
    static Path             basePath;
    static Path             realResources;
    static Path             testResources;
    static RtpProfileLinker rtpProfileLinker;

    @BeforeAll
    static void setup() {
        basePath = Paths.get("");
        realResources = Paths.get("src/main/resources");
        testResources = Paths.get("src/test/resources");
        ConfigurationFactoryImpl.buildFactory();
        rtpProfileLinker = new RtpProfileLinker();

    }

    @BeforeAll
    static void makeFakeWorlds() {
        Bukkit.setServer(new MockServer());
    }


    @Test
    void testThing() throws JrtpBaseException.ConfigurationException {
        File cfgFile = realResources.resolve("rtpProfiles/default-settings.yml").toFile();
        System.out.println(cfgFile.getAbsolutePath());
        if (!cfgFile.exists()) return;
        ConfigurationSection cfg = YamlConfiguration.loadConfiguration(cfgFile);

        RtpProfileLoader.loader.load(
            "test",
            cfg,
            null,
            rtpProfileLinker,
            System.out::println
        );

    }
}
