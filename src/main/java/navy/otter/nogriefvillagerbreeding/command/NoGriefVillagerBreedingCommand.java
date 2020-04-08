package navy.otter.nogriefvillagerbreeding.command;

import java.util.Arrays;
import java.util.Iterator;
import navy.otter.nogriefvillagerbreeding.NoGriefVillagerBreedingPlugin;
import navy.otter.nogriefvillagerbreeding.config.Configuration;
import navy.otter.nogriefvillagerbreeding.entity.BreedItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NoGriefVillagerBreedingCommand implements CommandExecutor {

  Configuration config = NoGriefVillagerBreedingPlugin.getConfiguration();

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
      @NotNull String s, @NotNull String[] strings) {
    if (!(commandSender instanceof Player)) {
      return false;
    }

    Player player = (Player) commandSender;
    Iterator<String> arg = Arrays.asList(strings).iterator();
    String option = arg.hasNext() ? arg.next() : "";

    if ("item".equals(option.toLowerCase())) {
      if (player.hasPermission("NoGriefVillagerBreeding.breedItem")) {
        giveControlItem(player);
      }
    }
    return true;
  }

  public void giveControlItem(Player player) {
    ItemStack breedItem = BreedItem.generateBreedItem();
    if (player.getInventory().firstEmpty() != -1) {
      player.getInventory().addItem(breedItem);
    } else {
      player.sendMessage(config.getInventoryFullMsg());
    }
  }
}
