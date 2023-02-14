package kalle.com.bedwars.configurations;

import kalle.com.bedwars.BedWars;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SettingsConfiguration {

    private static BedWars plugin;

    private static FileConfiguration config;
    private static File file;

    public SettingsConfiguration(BedWars plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        file = new File(plugin.getDataFolder(), "settings.yml");
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                initialize();
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.GREEN + "settings.yml file has been created!");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Could not create settings.yml file!");
            }
        }
    }

    public static void save() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Could not save settings.yml file!");
        }
    }

    private static void initialize() {
        config.set("Lobby World", "world");
        config.set("Lobby Spawn.x", 0.0);
        config.set("Lobby Spawn.y", 0.0);
        config.set("Lobby Spawn.z", 0.0);
        config.set("Lobby Spawn.yaw", 0.0);
        config.set("Game World", "world");
        config.set("Map Border Coordinates 1.x", 0.0);
        config.set("Map Border Coordinates 1.y", 0.0);
        config.set("Map Border Coordinates 1.z", 0.0);
        config.set("Map Border Coordinates 2.x", 0.0);
        config.set("Map Border Coordinates 2.y", 0.0);
        config.set("Map Border Coordinates 2.z", 0.0);
        save();
    }

    public static void setLobbySpawn(Location location) {
        config.set("Lobby Spawn.x", location.getX());
        config.set("Lobby Spawn.y", location.getY());
        config.set("Lobby Spawn.z", location.getZ());
        config.set("Lobby Spawn.yaw", location.getYaw());
        save();
    }

}
