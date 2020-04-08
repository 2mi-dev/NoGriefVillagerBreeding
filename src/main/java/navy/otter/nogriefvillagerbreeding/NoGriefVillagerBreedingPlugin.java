package navy.otter.nogriefvillagerbreeding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import navy.otter.nogriefvillagerbreeding.config.Configuration;
import navy.otter.nogriefvillagerbreeding.listener.EntityRightClickListener;
import navy.otter.nogriefvillagerbreeding.command.NoGriefVillagerBreedingCommand;
import navy.otter.nogriefvillagerbreeding.command.NoGriefVillagerBreedingTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class NoGriefVillagerBreedingPlugin extends JavaPlugin {

  private static NoGriefVillagerBreedingPlugin instance;
  private static Configuration config;
  private static List<UUID> villagerList = new ArrayList<>();

  @Override
  public void onEnable() {

    instance = this;
    config = new Configuration(this);

    this.getCommand("nogriefvillagerbreeding").setExecutor(new NoGriefVillagerBreedingCommand());
    this.getCommand("nogriefvillagerbreeding").setTabCompleter(new NoGriefVillagerBreedingTabCompleter());
    getServer().getPluginManager().registerEvents(new EntityRightClickListener(), this);
  }

  public static NoGriefVillagerBreedingPlugin getInstance() {
    return instance;
  }

  public static Configuration getConfiguration() {
    return config;
  }

  public static List<UUID> getVillagerList() {
    return villagerList;
  }
}
