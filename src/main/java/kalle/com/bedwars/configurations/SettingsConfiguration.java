package kalle.com.bedwars.configurations;

import kalle.com.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.GREEN + "settings.yml file has been created.");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Couldn't create settings.yml file.");
            }
        }
    }

    private static void save() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Couldn't save settings.yml file.");
        }
    }

    public static void setLobbySpawn(Location location) {
        config.set("Lobby.World", location.getWorld().getName());
        config.set("Lobby.Spawn.X", (int) Math.round(location.getX()));
        config.set("Lobby.Spawn.Y", (int) Math.round(location.getY()));
        config.set("Lobby.Spawn.Z", (int) Math.round(location.getZ()));
        //setting yaw to only 4 possible directions
        float yaw = location.getYaw();
        if (yaw <= 0) yaw += 360;
        yaw %= 360;
        if (yaw <= 45 || yaw > 315) {
            config.set("Lobby.Spawn.Yaw", 0);
        } else if (yaw <= 135) {
            config.set("Lobby.Spawn.Yaw", 90);
        } else if (yaw <= 225) {
            config.set("Lobby.Spawn.Yaw", 180);
        } else if (yaw <= 315) {
            config.set("Lobby.Spawn.Yaw", 270);
        }
        save();
    }

    public static void setGameBorder(int index, Location location) throws IllegalArgumentException {
        if (index != 1 && index != 2) throw new IllegalArgumentException();
        config.set("Game.World", location.getWorld().getName());
        config.set("Game.Border." + index + ".X", Math.round(location.getX()));
        config.set("Game.Border." + index + ".Y", Math.round(location.getY()));
        config.set("Game.Border." + index + ".Z", Math.round(location.getZ()));
        save();
    }

    public static World getGameWorld() throws NullPointerException {
        String name = config.getString("Game.World");
        World world = Bukkit.getServer().getWorld(name);
        if (world == null) throw new NullPointerException();
        return world;
    }

    public static Location getLobbySpawn() throws NullPointerException {
        World world = Bukkit.getServer().getWorld(config.getString("Lobby.World"));
        if (world == null) throw new NullPointerException();
        int x = config.getInt("Lobby.Spawn.X");
        int y = config.getInt("Lobby.Spawn.Y");
        int z = config.getInt("Lobby.Spawn.Z");
        int yaw = config.getInt("Lobby.Spawn.Yaw");
        Location location = new Location(world, x, y, z, yaw, 0.0f);
        return location;
    }

    public static void setReady(boolean ready) {
        config.set("Ready to play", ready);
        save();
    }

    public static boolean isReady() {
        boolean ready = config.getBoolean("Ready to play");
        return ready;
    }

    public static void setMinPlayers(int min) {
        config.set("Lobby.Minimum Amount of Players", min);
        save();
    }

    public static int getMinPlayers() throws NullPointerException {
        int min = config.getInt("Lobby.Minimum Amount of Players");
        if (min == 0) throw new NullPointerException();
        return min;
    }

}
