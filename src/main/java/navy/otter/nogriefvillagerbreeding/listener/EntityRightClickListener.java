package navy.otter.nogriefvillagerbreeding.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import navy.otter.nogriefvillagerbreeding.config.Configuration;
import navy.otter.nogriefvillagerbreeding.NoGriefVillagerBreedingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityRightClickListener implements Listener {

  NoGriefVillagerBreedingPlugin plugin = NoGriefVillagerBreedingPlugin.getInstance();
  Configuration config = NoGriefVillagerBreedingPlugin.getConfiguration();
  List<UUID> villagerList = NoGriefVillagerBreedingPlugin.getVillagerList();
  HashSet<Player> singleUse = new HashSet<>();

  @EventHandler
  public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
    Entity entity = e.getRightClicked();
    Player p = e.getPlayer();


    if(!(entity instanceof Villager)) {
      return;
    }

    ItemStack mainHandItemStack = p.getInventory().getItemInMainHand();

    if(mainHandItemStack.getAmount() == 0) {
      return;
    }

    Map<Enchantment, Integer> enchantments = mainHandItemStack.getEnchantments();
    if(enchantments.get(Enchantment.DURABILITY) != 137
        || enchantments.get(Enchantment.ARROW_DAMAGE) != 233
        || enchantments.get(Enchantment.LURE) != 99
        || enchantments.size() != 3) {
      return;
    }

    Villager villager = (Villager) entity;
    UUID villagerUuid = villager.getUniqueId();
    if(!villagerList.contains(villagerUuid) && villager.isAdult()
    && !villager.getInventory().contains(Material.BREAD, 1)) {
      villager.setBreed(true);
      villager.getInventory().clear();
      villager.getInventory().addItem(new ItemStack(Material.BREAD, 3));
      e.setCancelled(true);
      mainHandItemStack.setAmount(mainHandItemStack.getAmount() -1);
      villagerList.add(villagerUuid);

      Runnable r = () -> villagerList.remove(villagerUuid);
      Bukkit.getScheduler().runTaskLater(plugin, r, config.getVillagerBreedCooldown());
    } else {
      p.sendMessage(config.getVillagerNotReadyToBreedMsg());
      e.setCancelled(true);
    }
  }
}
