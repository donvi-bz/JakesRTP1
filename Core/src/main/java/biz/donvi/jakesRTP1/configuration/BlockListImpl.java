package biz.donvi.jakesRTP1.configuration;

import biz.donvi.jakesRTP1API.configuration.BlockList;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.EnumSet;

import static org.bukkit.Material.*;

public class BlockListImpl implements BlockList {

    public static final BlockListImpl DEFAULTS;

    protected EnumSet<Material> safeToBeIn   = null;
    protected EnumSet<Material> unsafeToBeOn = null;
    protected EnumSet<Material> treeLeaves   = null;
    protected EnumSet<Biome>    unsafeBiomes = null;

    @Override
    public EnumSet<Material> getSafeToBeIn() {
        return safeToBeIn;
    }

    @Override
    public void setSafeToBeIn(EnumSet<Material> safeToBeIn) {
        this.safeToBeIn = safeToBeIn;
    }

    @Override
    public EnumSet<Material> getUnsafeToBeOn() {
        return unsafeToBeOn;
    }

    @Override
    public void setUnsafeToBeOn(EnumSet<Material> unsafeToBeOn) {
        this.unsafeToBeOn = unsafeToBeOn;
    }

    @Override
    public EnumSet<Material> getTreeLeaves() {
        return treeLeaves;
    }

    @Override
    public void setTreeLeaves(EnumSet<Material> treeLeaves) {
        this.treeLeaves = treeLeaves;
    }

    @Override
    public EnumSet<Biome> getUnsafeBiomes() {
        return unsafeBiomes;
    }

    @Override
    public void setUnsafeBiomes(EnumSet<Biome> unsafeBiomes) {
        this.unsafeBiomes = unsafeBiomes;
    }

    static {
        DEFAULTS = new BlockListImpl();
        try {
            DEFAULTS.setSafeToBeIn(EnumSet.of(
                AIR, SNOW, FERN, LARGE_FERN, VINE, GRASS, TALL_GRASS, GLOW_LICHEN, MOSS_CARPET, GLOW_BERRIES));
            DEFAULTS.setUnsafeToBeOn(EnumSet.of(
                LAVA, MAGMA_BLOCK, WATER, AIR, CAVE_AIR, VOID_AIR, CACTUS, SEAGRASS, KELP, TALL_SEAGRASS, LILY_PAD,
                BAMBOO, BAMBOO_SAPLING, SMALL_DRIPLEAF, BIG_DRIPLEAF, BIG_DRIPLEAF_STEM, POINTED_DRIPSTONE));
            DEFAULTS.setTreeLeaves(EnumSet.of(
                ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, OAK_LEAVES,
                SPRUCE_LEAVES, AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES));
            DEFAULTS.setUnsafeBiomes(EnumSet.noneOf(Biome.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
