package kalle.com.bedwars.configurations;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.ColorIterator;
import kalle.com.bedwars.Team;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TeamsConfiguration {

    private static BedWars plugin;

    private static FileConfiguration config;
    private static File file;

    public TeamsConfiguration(BedWars plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        file = new File(plugin.getDataFolder(), "teams.yml");
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.GREEN + "teams.yml file has been created.");
            } catch (IOException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Couldn't create teams.yml file.");
            }
        }
    }

    private static void save() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Couldn't save teams.yml file.");
        }
    }

    public static List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();
        for (ColorIterator color : ColorIterator.values()) {
            String identifier = color.toString();
            try {
                int size = config.getInt("Team " + identifier + ".Size");
                int playerX = config.getInt("Team " + identifier + ".Spawn.X");
                int playerY = config.getInt("Team " + identifier + ".Spawn.Y");
                int playerZ = config.getInt("Team " + identifier + ".Spawn.Z");
                int playerYaw = config.getInt("Team " + identifier + ".Spawn.Yaw");
                int bedX = config.getInt("Team " + identifier + ".Bed.X");
                int bedY = config.getInt("Team " + identifier + ".Bed.Y");
                int bedZ = config.getInt("Team " + identifier + ".Bed.Z");
                String bedDirection = config.getString("Team " + identifier + ".Bed.Direction");
                Team team = new Team(color.toString(), size, new int[]{playerX, playerY, playerZ, playerYaw}, new int[]{bedX, bedY, bedZ}, bedDirection);
                teams.add(team);
            } catch (NullPointerException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Something went wrong while loading the Teams from the teams.yml file, " +
                        "a value is missing.");
                continue;
            } catch (IllegalArgumentException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Something went wrong while loading the Teams from the teams.yml file, "
                        + "an illegal argument was found. Arguments of Team: " + ChatColor.GOLD + color.toString() + ChatColor.RED + " are faulty.");
                continue;
            }
        }
        return teams;
    }

    public static void setTeamSize(String name, int size) throws IllegalArgumentException {
        try {
            ChatColor color = ChatColor.valueOf(name); //check if team exists
            if (!color.isColor()) throw new IllegalArgumentException(); //code is a format and not a color
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException();
        }
        config.set("Team " + name + ".Size", size);
        save();
    }

    public static void setTeamSpawn(String name, Location location) throws IllegalArgumentException {
        ChatColor color = ChatColor.valueOf(name); //check if team exists
        if (!color.isColor()) throw new IllegalArgumentException(); //code is a format and not a color
        config.set("Team " + name + ".Spawn.X", (int) Math.round(location.getX()));
        config.set("Team " + name + ".Spawn.Y", (int) Math.round(location.getY()));
        config.set("Team " + name + ".Spawn.Z", (int) Math.round(location.getZ()));
        //setting yaw to only 4 possible directions
        float yaw = location.getYaw();
        if (yaw <= 0) yaw += 360;
        yaw %= 360;
        if (yaw <= 45 || yaw > 315) {
            config.set("Team " + name + ".Spawn.Yaw", 0);
        } else if (yaw <= 135) {
            config.set("Team " + name + ".Spawn.Yaw", 90);
        } else if (yaw <= 225) {
            config.set("Team " + name + ".Spawn.Yaw", 180);
        } else if (yaw <= 315) {
            config.set("Team " + name + ".Spawn.Yaw", 270);
        }
        save();
    }

    public static void setTeamBed(String name, Location location) {
        ChatColor color = ChatColor.valueOf(name); //check if team exists
        if (!color.isColor()) throw new IllegalArgumentException(); //code is a format and not a color
        float yaw = location.getYaw();
        if (yaw <= 0) yaw += 360;
        yaw %= 360;
        if (yaw <= 45 || yaw > 315) {
            config.set("Team " + name + ".Bed.Direction", "NORTH");
        } else if (yaw <= 135) {
            config.set("Team " + name + ".Bed.Direction", "EAST");
        } else if (yaw <= 225) {
            config.set("Team " + name + ".Bed.Direction", "SOUTH");
        } else if (yaw <= 315) {
            config.set("Team " + name + ".Bed.Direction", "WEST");
        }
        config.set("Team " + name + ".Bed.X", (int) Math.round(location.getX()));
        config.set("Team " + name + ".Bed.Y", (int) Math.round(location.getY()));
        config.set("Team " + name + ".Bed.Z", (int) Math.round(location.getZ()));
        save();
    }

}
