package biz.donvi.jakesRTP1_mock;

import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MockServer extends MockServer_blank {

    public List<World> worldList = new ArrayList<>();

    public MockServer() {
        MockWorld world = new MockWorld();
        worldList.add(world);
        world = new MockWorld();
        world.name = "world_nether";
        worldList.add(world);
        world = new MockWorld();
        world.name = "world_the_end";
        worldList.add(world);
        world = new MockWorld();
        world.name = "potato";
        worldList.add(world);
    }


    @Override
    public List<World> getWorlds() {
        return worldList;
    }

    @Override
    public World getWorld(String name) {
        for (World world : worldList) {
            if (world.getName().equals(name))
                return world;
        }
        return null;
    }

    @Override
    public String getName() {
        return "mock_server";
    }

    @Override
    public String getVersion() {
        return "mock_version";
    }

    @Override
    public String getBukkitVersion() {
        return "mock_bukkit_version";
    }

    Logger logger = Logger.getAnonymousLogger();

    @Override
    public Logger getLogger() {
        return logger;
    }
}
