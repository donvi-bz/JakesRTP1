package biz.donvi.jakesRTP1_mock;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.*;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.*;
import org.bukkit.loot.LootTable;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class MockServer_blank implements org.bukkit.Server {
    @Override
    public String getName() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getVersion() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getBukkitVersion() {
        throw new BlankMockMethodException();
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getMaxPlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getPort() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getViewDistance() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getIp() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getWorldType() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getGenerateStructures() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getMaxWorldSize() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getAllowEnd() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getAllowNether() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean hasWhitelist() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setWhitelist(boolean value) {

    }

    @Override
    public boolean isWhitelistEnforced() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setWhitelistEnforced(boolean value) {

    }

    @Override
    public Set<OfflinePlayer> getWhitelistedPlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public void reloadWhitelist() {

    }

    @Override
    public int broadcastMessage(String message) {
        throw new BlankMockMethodException();
    }

    @Override
    public String getUpdateFolder() {
        throw new BlankMockMethodException();
    }

    @Override
    public File getUpdateFolderFile() {
        throw new BlankMockMethodException();
    }

    @Override
    public long getConnectionThrottle() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getTicksPerAnimalSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getTicksPerMonsterSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getTicksPerWaterSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getTicksPerWaterAmbientSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getTicksPerAmbientSpawns() {
        throw new BlankMockMethodException();
    }

    @Override
    public Player getPlayer(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public Player getPlayerExact(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Player> matchPlayer(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public Player getPlayer(UUID id) {
        throw new BlankMockMethodException();
    }

    @Override
    public PluginManager getPluginManager() {
        throw new BlankMockMethodException();
    }

    @Override
    public BukkitScheduler getScheduler() {
        throw new BlankMockMethodException();
    }

    @Override
    public ServicesManager getServicesManager() {
        throw new BlankMockMethodException();
    }

    @Override
    public List<World> getWorlds() {
        throw new BlankMockMethodException();
    }

    @Override
    public World createWorld(WorldCreator creator) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadWorld(String name, boolean save) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean unloadWorld(World world, boolean save) {
        throw new BlankMockMethodException();
    }

    @Override
    public World getWorld(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public World getWorld(UUID uid) {
        throw new BlankMockMethodException();
    }

    @Override
    public MapView getMap(int id) {
        throw new BlankMockMethodException();
    }

    @Override
    public MapView createMap(World world) {
        throw new BlankMockMethodException();
    }

    @Override
    public ItemStack createExplorerMap(
        World world, Location location, StructureType structureType
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public ItemStack createExplorerMap(
        World world, Location location, StructureType structureType, int radius, boolean findUnexplored
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public void reload() {

    }

    @Override
    public void reloadData() {

    }

    @Override
    public Logger getLogger() {
        throw new BlankMockMethodException();
    }

    @Override
    public PluginCommand getPluginCommand(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public void savePlayers() {

    }

    @Override
    public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Recipe> getRecipesFor(ItemStack result) {
        throw new BlankMockMethodException();
    }

    @Override
    public Recipe getRecipe(NamespacedKey recipeKey) {
        throw new BlankMockMethodException();
    }

    @Override
    public Iterator<Recipe> recipeIterator() {
        throw new BlankMockMethodException();
    }

    @Override
    public void clearRecipes() {

    }

    @Override
    public void resetRecipes() {

    }

    @Override
    public boolean removeRecipe(NamespacedKey key) {
        throw new BlankMockMethodException();
    }

    @Override
    public Map<String, String[]> getCommandAliases() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getSpawnRadius() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setSpawnRadius(int value) {

    }

    @Override
    public boolean getOnlineMode() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean getAllowFlight() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isHardcore() {
        throw new BlankMockMethodException();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public int broadcast(String message, String permission) {
        throw new BlankMockMethodException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String name) {
        throw new BlankMockMethodException();
    }

    @Override
    public OfflinePlayer getOfflinePlayer(UUID id) {
        throw new BlankMockMethodException();
    }

    @Override
    public Set<String> getIPBans() {
        throw new BlankMockMethodException();
    }

    @Override
    public void banIP(String address) {

    }

    @Override
    public void unbanIP(String address) {

    }

    @Override
    public Set<OfflinePlayer> getBannedPlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public BanList getBanList(BanList.Type type) {
        throw new BlankMockMethodException();
    }

    @Override
    public Set<OfflinePlayer> getOperators() {
        throw new BlankMockMethodException();
    }

    @Override
    public GameMode getDefaultGameMode() {
        throw new BlankMockMethodException();
    }

    @Override
    public void setDefaultGameMode(GameMode mode) {

    }

    @Override
    public ConsoleCommandSender getConsoleSender() {
        throw new BlankMockMethodException();
    }

    @Override
    public File getWorldContainer() {
        throw new BlankMockMethodException();
    }

    @Override
    public OfflinePlayer[] getOfflinePlayers() {
        throw new BlankMockMethodException();
    }

    @Override
    public Messenger getMessenger() {
        throw new BlankMockMethodException();
    }

    @Override
    public HelpMap getHelpMap() {
        throw new BlankMockMethodException();
    }

    @Override
    public Inventory createInventory(
        InventoryHolder owner, InventoryType type
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
        throw new BlankMockMethodException();
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public Merchant createMerchant(String title) {
        throw new BlankMockMethodException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getAnimalSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getWaterAmbientSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public int getAmbientSpawnLimit() {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean isPrimaryThread() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getMotd() {
        throw new BlankMockMethodException();
    }

    @Override
    public String getShutdownMessage() {
        throw new BlankMockMethodException();
    }

    @Override
    public Warning.WarningState getWarningState() {
        throw new BlankMockMethodException();
    }

    @Override
    public ItemFactory getItemFactory() {
        throw new BlankMockMethodException();
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        throw new BlankMockMethodException();
    }

    @Override
    public CachedServerIcon getServerIcon() {
        throw new BlankMockMethodException();
    }

    @Override
    public CachedServerIcon loadServerIcon(File file) throws IllegalArgumentException, Exception {
        throw new BlankMockMethodException();
    }

    @Override
    public CachedServerIcon loadServerIcon(BufferedImage image) throws IllegalArgumentException, Exception {
        throw new BlankMockMethodException();
    }

    @Override
    public void setIdleTimeout(int threshold) {

    }

    @Override
    public int getIdleTimeout() {
        throw new BlankMockMethodException();
    }

    @Override
    public ChunkGenerator.ChunkData createChunkData(World world) {
        throw new BlankMockMethodException();
    }

    @Override
    public BossBar createBossBar(
        String title, BarColor color, BarStyle style, BarFlag... flags
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public KeyedBossBar createBossBar(
        NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... flags
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public Iterator<KeyedBossBar> getBossBars() {
        throw new BlankMockMethodException();
    }

    @Override
    public KeyedBossBar getBossBar(NamespacedKey key) {
        throw new BlankMockMethodException();
    }

    @Override
    public boolean removeBossBar(NamespacedKey key) {
        throw new BlankMockMethodException();
    }

    @Override
    public Entity getEntity(UUID uuid) {
        throw new BlankMockMethodException();
    }

    @Override
    public Advancement getAdvancement(NamespacedKey key) {
        throw new BlankMockMethodException();
    }

    @Override
    public Iterator<Advancement> advancementIterator() {
        throw new BlankMockMethodException();
    }

    @Override
    public BlockData createBlockData(Material material) {
        throw new BlankMockMethodException();
    }

    @Override
    public BlockData createBlockData(
        Material material, Consumer<BlockData> consumer
    ) {
        throw new BlankMockMethodException();
    }

    @Override
    public BlockData createBlockData(String data) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public BlockData createBlockData(Material material, String data) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Keyed> Tag<T> getTag(String registry, NamespacedKey tag, Class<T> clazz) {
        throw new BlankMockMethodException();
    }

    @Override
    public <T extends Keyed> Iterable<Tag<T>> getTags(String registry, Class<T> clazz) {
        throw new BlankMockMethodException();
    }

    @Override
    public LootTable getLootTable(NamespacedKey key) {
        throw new BlankMockMethodException();
    }

    @Override
    public List<Entity> selectEntities(CommandSender sender, String selector) throws IllegalArgumentException {
        throw new BlankMockMethodException();
    }

    @Override
    public UnsafeValues getUnsafe() {
        throw new BlankMockMethodException();
    }

    @Override
    public Spigot spigot() {
        throw new BlankMockMethodException();
    }

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {

    }

    @Override
    public Set<String> getListeningPluginChannels() {
        throw new BlankMockMethodException();
    }
}
