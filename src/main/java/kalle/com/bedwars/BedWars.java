package kalle.com.bedwars;

import jdk.incubator.vector.VectorOperators;
import kalle.com.bedwars.commands.*;
import kalle.com.bedwars.configurations.SettingsConfiguration;
import kalle.com.bedwars.configurations.TeamsConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWars extends JavaPlugin {

    public static String PluginTag = ChatColor.DARK_PURPLE + "[BedWars] " + ChatColor.RESET;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        registerEvents();
        setupConfigurations();
        getServer().getConsoleSender().sendMessage(PluginTag + ChatColor.GREEN + "Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(PluginTag + ChatColor.RED + "Plugin has been disabled.");
    }

    private void registerCommands() {
        getCommand("setteamsize").setExecutor(new SetTeamSizeCommand());
        getCommand("setteamspawn").setExecutor(new SetTeamSpawnCommand());
        getCommand("setteambed").setExecutor(new SetTeamBedCommand());
        getCommand("test").setExecutor(new TestCommand());
    }

    private void registerEvents() {

    }

    private void setupConfigurations() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        SettingsConfiguration settings = new SettingsConfiguration(this);
        settings.setup();
        TeamsConfiguration teams = new TeamsConfiguration(this);
        teams.setup();
    }


}
