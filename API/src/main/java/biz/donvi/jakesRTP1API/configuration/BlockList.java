package biz.donvi.jakesRTP1API.configuration;

import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.EnumSet;

public interface BlockList {
    EnumSet<Material> getSafeToBeIn();

    void setSafeToBeIn(EnumSet<Material> safeToBeIn);

    EnumSet<Material> getUnsafeToBeOn();

    void setUnsafeToBeOn(EnumSet<Material> unsafeToBeOn);

    EnumSet<Material> getTreeLeaves();

    void setTreeLeaves(EnumSet<Material> treeLeaves);

    EnumSet<Biome> getUnsafeBiomes();

    void setUnsafeBiomes(EnumSet<Biome> unsafeBiomes);
}
