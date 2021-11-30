package biz.donvi.jakesRTP1_mock;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

public class MockWorld_blank implements World {
    @Override
    public Block getBlockAt(int x, int y, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Block getBlockAt(Location location) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        throw new BlankMockMethodException();
    }

    @Override
    public Block getHighestBlockAt(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Block getHighestBlockAt(Location location) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getHighestBlockYAt(Location location, HeightMap heightMap) {
        throw new BlankMockMethodException();
    }

    @Override
    public Block getHighestBlockAt(int x, int z, HeightMap heightMap) {
        throw new BlankMockMethodException();
    }

    @Override
    public Block getHighestBlockAt(Location location, HeightMap heightMap) {
        throw new BlankMockMethodException();
    }

    @Override
    public Chunk getChunkAt(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Chunk getChunkAt(Location location) {
        throw new BlankMockMethodException();
    }

    @Override
    public Chunk getChunkAt(Block block) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isChunkLoaded(Chunk chunk) {
        throw new BlankMockMethodException();
    }

    @Override
    public Chunk[] getLoadedChunks() {
        throw new BlankMockMethodException();
    }

    @Override
    public void loadChunk(Chunk chunk) {

    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isChunkGenerated(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isChunkInUse(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public void loadChunk(int x, int z) {

    }

    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadChunk(Chunk chunk) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadChunk(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean save) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadChunkRequest(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean regenerateChunk(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean refreshChunk(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isChunkForceLoaded(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public void setChunkForceLoaded(int x, int z, boolean forced) {

    }

    @Override
    public Collection<Chunk> getForceLoadedChunks() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean addPluginChunkTicket(int x, int z, Plugin plugin) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean removePluginChunkTicket(int x, int z, Plugin plugin) {
        throw new BlankMockMethodException();
    }

    @Override
    public void removePluginChunkTickets(Plugin plugin) {

    }

    @Override
    public Collection<Plugin> getPluginChunkTickets(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Map<Plugin, Collection<Chunk>> getPluginChunkTickets() {
        throw new BlankMockMethodException();
    }

    @Override
    public Item dropItem(Location location, ItemStack item) {
        throw new BlankMockMethodException();
    }

    @Override
    public Item dropItem(Location location, ItemStack item, Consumer<Item> function) {
        throw new BlankMockMethodException();
    }

    @Override
    public Item dropItemNaturally(Location location, ItemStack item) {
        throw new BlankMockMethodException();
    }

    @Override
    public Item dropItemNaturally(
        Location location, ItemStack item, Consumer<Item> function
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public Arrow spawnArrow(
        Location location, Vector direction, float speed, float spread
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends AbstractArrow> T spawnArrow(
        Location location, Vector direction, float speed, float spread, Class<T> clazz
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean generateTree(Location location, TreeType type) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        throw new BlankMockMethodException();
    }

    @Override
    public Entity spawnEntity(Location loc, EntityType type) {
        throw new BlankMockMethodException();
    }

    @Override
    public LightningStrike strikeLightning(Location loc) {
        throw new BlankMockMethodException();
    }

    @Override
    public LightningStrike strikeLightningEffect(Location loc) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Entity> getEntities() {
        throw new BlankMockMethodException();
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> cls) {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Player> getPlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(
        Location location, double x, double y, double z, Predicate<Entity> filter
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(BoundingBox boundingBox) {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(
        BoundingBox boundingBox, Predicate<Entity> filter
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceEntities(
        Location start, Vector direction, double maxDistance, Predicate<Entity> filter
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceEntities(
        Location start, Vector direction, double maxDistance, double raySize, Predicate<Entity> filter
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(
        Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(
        Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode,
        boolean ignorePassableBlocks
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public RayTraceResult rayTrace(
        Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode,
        boolean ignorePassableBlocks, double raySize, Predicate<Entity> filter
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public String getName() {
        throw new BlankMockMethodException();
    }

    @Override
    public UUID getUID() {
        throw new BlankMockMethodException();
    }

    @Override
    public Location getSpawnLocation() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean setSpawnLocation(Location location) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean setSpawnLocation(int x, int y, int z, float angle) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean setSpawnLocation(int x, int y, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public long getTime() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTime(long time) {

    }

    @Override
    public long getFullTime() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setFullTime(long time) {

    }

    @Override
    public long getGameTime() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean hasStorm() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setStorm(boolean hasStorm) {

    }

    @Override
    public int getWeatherDuration() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setWeatherDuration(int duration) {

    }

    @Override
    public boolean isThundering() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setThundering(boolean thundering) {

    }

    @Override
    public int getThunderDuration() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setThunderDuration(int duration) {

    }

    @Override
    public boolean isClearWeather() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setClearWeatherDuration(int duration) {

    }

    @Override
    public int getClearWeatherDuration() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(
        double x, double y, double z, float power, boolean setFire, boolean breakBlocks, Entity source
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(Location loc, float power) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean createExplosion(
        Location loc, float power, boolean setFire, boolean breakBlocks, Entity source
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public Environment getEnvironment() {
        throw new BlankMockMethodException();
    }

    @Override
    public long getSeed() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getPVP() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setPVP(boolean pvp) {

    }

    @Override
    public ChunkGenerator getGenerator() {
        throw new BlankMockMethodException();
    }

    @Override
    public void save() {

    }

    @Override
    public List<BlockPopulator> getPopulators() {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function)
    throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, MaterialData data) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, Material material, byte data)
    throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int data) {

    }

    @Override
    public void playEffect(Location location, Effect effect, int data, int radius) {

    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data) {

    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data, int radius) {

    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTemp) {
        throw new BlankMockMethodException();
    }

    @Override
    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {

    }

    @Override
    public boolean getAllowAnimals() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getAllowMonsters() {
        throw new BlankMockMethodException();
    }

    @Override
    public Biome getBiome(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public void setBiome(int x, int z, Biome bio) {

    }

    @Override
    public void setBiome(int x, int y, int z, Biome bio) {

    }

    @Override
    public double getTemperature(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public double getTemperature(int x, int y, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public double getHumidity(int x, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public double getHumidity(int x, int y, int z) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getMinHeight() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getMaxHeight() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getSeaLevel() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setKeepSpawnInMemory(boolean keepLoaded) {

    }

    @Override
    public boolean isAutoSave() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setAutoSave(boolean value) {

    }

    @Override
    public void setDifficulty(Difficulty difficulty) {

    }

    @Override
    public Difficulty getDifficulty() {
        throw new BlankMockMethodException();
    }

    @Override
    public File getWorldFolder() {
        throw new BlankMockMethodException();
    }

    @Override
    public WorldType getWorldType() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean canGenerateStructures() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isHardcore() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setHardcore(boolean hardcore) {

    }

    @Override
    public long getTicksPerAnimalSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {

    }

    @Override
    public long getTicksPerMonsterSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {

    }

    @Override
    public long getTicksPerWaterSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTicksPerWaterSpawns(int ticksPerWaterSpawns) {

    }

    @Override
    public long getTicksPerWaterAmbientSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTicksPerWaterAmbientSpawns(int ticksPerAmbientSpawns) {

    }

    @Override
    public long getTicksPerAmbientSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns) {

    }

    @Override
    public int getMonsterSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setMonsterSpawnLimit(int limit) {

    }

    @Override
    public int getAnimalSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setAnimalSpawnLimit(int limit) {

    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setWaterAnimalSpawnLimit(int limit) {

    }

    @Override
    public int getWaterAmbientSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setWaterAmbientSpawnLimit(int limit) {

    }

    @Override
    public int getAmbientSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setAmbientSpawnLimit(int limit) {

    }

    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch) {

    }

    @Override
    public void playSound(Location location, String sound, float volume, float pitch) {

    }

    @Override
    public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch) {

    }

    @Override
    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch) {

    }

    @Override
    public String[] getGameRules() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getGameRuleValue(String rule) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean setGameRuleValue(String rule, String value) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isGameRule(String rule) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T> T getGameRuleValue(GameRule<T> rule) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T> T getGameRuleDefault(GameRule<T> rule) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T> boolean setGameRule(GameRule<T> rule, T newValue) {
        throw new BlankMockMethodException();
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw new BlankMockMethodException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count) {

    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count) {

    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, T data) {

    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {

    }

    @Override
    public void spawnParticle(
        Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ
    ) {

    }

    @Override
    public void spawnParticle(
        Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, T data
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
        T data
    ) {

    }

    @Override
    public void spawnParticle(
        Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra
    ) {

    }

    @Override
    public void spawnParticle(
        Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
        double extra
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra,
        T data
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
        double extra, T data
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra,
        T data, boolean force
    ) {

    }

    @Override
    public <T> void spawnParticle(
        Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ,
        double extra, T data, boolean force
    ) {

    }

    @Override
    public Location locateNearestStructure(
        Location origin, StructureType structureType, int radius, boolean findUnexplored
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getViewDistance() {
        throw new BlankMockMethodException();
    }

    @Override
    public Spigot spigot() {
        throw new BlankMockMethodException();
    }

    @Override
    public Raid locateNearestRaid(Location location, int radius) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Raid> getRaids() {
        throw new BlankMockMethodException();
    }

    @Override
    public DragonBattle getEnderDragonBattle() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {

    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        throw new BlankMockMethodException();
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {

    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {

    }

    @Override
    public Set<String> getListeningPluginChannels() {
        throw new BlankMockMethodException();
    }
}
