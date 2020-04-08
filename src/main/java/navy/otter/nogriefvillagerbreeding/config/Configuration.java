package navy.otter.nogriefvillagerbreeding.config;

import java.util.ArrayList;
import java.util.List;
import navy.otter.nogriefvillagerbreeding.NoGriefVillagerBreedingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

  private static class Key {

    private static final String BREED_ITEM_TYPE = "breed-item-type";
    private static final String BREED_ITEM_LORE = "breed-item-lore";
    private static final String BREED_ITEM_NAME = "breed-item-name";

    private static final String VILLAGER_BREED_COOLDOWN = "villager-breed-cooldown";

    private static final String MSG_PREFIX = "msg-prefix";
    private static final String INVENTORY_FULL_MSG = "inventory-full-msg";
    private static final String VILLAGER_NOT_READY_TO_BREED_MSG = "villager-not-ready-to-breed-msg";
  }

  private Material breedItemType = Material.AIR;
  private List<String> breedItemLore = new ArrayList<>();
  private String breedItemName = "";

  private long villagerBreedCooldown = 0;

  private String msgPrefix = "";
  private String inventoryFullMsg = "";
  private String villagerNotReadyToBreedMsg = "";

  public Configuration(NoGriefVillagerBreedingPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    String itemMaterial = config.getString(Key.BREED_ITEM_TYPE);
    if (itemMaterial == null) {
      Bukkit.getLogger().severe("\"villager-breed-item\" not found, check your config!");
      Bukkit.getPluginManager().disablePlugin(plugin);
      return;
    }
    this.breedItemType = Material.matchMaterial(itemMaterial);
    if (this.breedItemType == null) {
      Bukkit.getLogger().severe("\"villager-breed-item\" is invalid, check your config!");
      Bukkit.getPluginManager().disablePlugin(plugin);
      return;
    }

    this.breedItemLore = new ArrayList<>();
    List<String> breedItemLoreLines = plugin.getConfig().getStringList(Key.BREED_ITEM_LORE);
    for (String breedItemLoreLine : breedItemLoreLines) {
      if (breedItemLoreLine == null) {
        Bukkit.getLogger().info(() -> "Lore contains errors, use \"\" for empty line.");
        continue;
      }
      this.breedItemLore.add(ChatColor.translateAlternateColorCodes('&', breedItemLoreLine));
    }

    String rawBreedItemName = config.getString(Key.BREED_ITEM_NAME);
    if(rawBreedItemName != null) {
      this.breedItemName = ChatColor.translateAlternateColorCodes('&', rawBreedItemName);
    } else {
      this.breedItemName = "";
    }

    this.villagerBreedCooldown = config.getLong(Key.VILLAGER_BREED_COOLDOWN) * 20;
    if(villagerBreedCooldown == 0) {
      Bukkit.getLogger().warning("Using 0 as \"villager-breed-cooldown\" or leaving it empty is not recommended!");
    }

    String rawMsgPrefix = config.getString(Key.MSG_PREFIX);
    if(rawMsgPrefix != null) {
      this.msgPrefix = ChatColor.translateAlternateColorCodes('&', rawMsgPrefix);
    }

    this.inventoryFullMsg = this.msgPrefix + config.getString(Key.INVENTORY_FULL_MSG);
    this.villagerNotReadyToBreedMsg = this.msgPrefix + config.getString(Key.VILLAGER_NOT_READY_TO_BREED_MSG);
  }

  public Material getBreedItemType() {
    return breedItemType;
  }

  public List<String> getBreedItemLore() {
    return breedItemLore;
  }

  public String getBreedItemName() {
    return breedItemName;
  }

  public long getVillagerBreedCooldown() {
    return villagerBreedCooldown;
  }

  public String getInventoryFullMsg() {
    return inventoryFullMsg;
  }

  public String getVillagerNotReadyToBreedMsg() {
    return villagerNotReadyToBreedMsg;
  }
}
