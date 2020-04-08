package navy.otter.nogriefvillagerbreeding.entity;

import java.util.Objects;
import navy.otter.nogriefvillagerbreeding.config.Configuration;
import navy.otter.nogriefvillagerbreeding.NoGriefVillagerBreedingPlugin;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BreedItem {

  public static ItemStack generateBreedItem() {

    Configuration config = NoGriefVillagerBreedingPlugin.getConfiguration();

    ItemStack breedItem = new ItemStack(config.getBreedItemType());
    breedItem.setAmount(1);
    ItemMeta breedItemMeta = Objects.requireNonNull(breedItem.getItemMeta());
    String displayName = config.getBreedItemName();
    if(!displayName.equals("")) {
      breedItemMeta.setDisplayName(displayName);
    }
    breedItemMeta.setLore(config.getBreedItemLore());
    breedItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    breedItem.setItemMeta(breedItemMeta);
    breedItem.addUnsafeEnchantment(Enchantment.DURABILITY, 137);
    breedItem.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 233);
    breedItem.addUnsafeEnchantment(Enchantment.LURE, 99);
    return breedItem;
  }
}
