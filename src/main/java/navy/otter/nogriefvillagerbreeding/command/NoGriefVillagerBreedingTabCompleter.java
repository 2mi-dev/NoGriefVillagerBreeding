package navy.otter.nogriefvillagerbreeding.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoGriefVillagerBreedingTabCompleter implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender,
      @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
    ArrayList<String> list = new ArrayList<>();
    if (!(commandSender instanceof Player)) {
      return new ArrayList<>();
    }

    if (strings.length <= 1) {
      if (commandSender.hasPermission("NoGriefVillagerBreeding.breedItem")) {
        list.add("item");
      }
      return list
          .stream()
          .filter((string) -> string.startsWith(strings[0]))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }
}
