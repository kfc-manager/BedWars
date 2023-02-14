package kalle.com.bedwars.configurations;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.MyColor;
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
                initialize();
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

    private static void initialize() {
        for (MyColor color : MyColor.values()) {
            String identifier = color.toString();
            config.set("Team " + identifier + ".Size",0);
            config.set("Team " + identifier + ".Spawn.X",0.0);
            config.set("Team " + identifier + ".Spawn.Y",0.0);
            config.set("Team " + identifier + ".Spawn.Z",0.0);
            config.set("Team " + identifier + ".Spawn.Yaw",0.0);
            config.set("Team " + identifier + ".Spawn.Pitch",0.0);
            config.set("Team " + identifier + ".Bed.X",0.0);
            config.set("Team " + identifier + ".Bed.Y",0.0);
            config.set("Team " + identifier + ".Bed.Z",0.0);
            config.set("Team " + identifier + ".Bed.Direction","NORTH");
        }
        save();
    }

    public static List<Team> loadTeams() {
        List<Team> teams = new ArrayList<>();
        for (MyColor color : MyColor.values()) {
            String identifier = color.toString();
            try {
                int size = config.getInt("Team " + identifier + ".Size");
                int x = config.getInt("Team " + identifier + ".Spawn.X");
                int y = config.getInt("Team " + identifier + ".Spawn.Y");
                int z = config.getInt("Team " + identifier + ".Spawn.Z");
                double yaw = config.getDouble("Team " + identifier + ".Spawn.Yaw");
                int bedX = config.getInt("Team " + identifier + ".Bed.X");
                int bedY = config.getInt("Team " + identifier + ".Bed.Y");
                int bedZ = config.getInt("Team " + identifier + ".Bed.Z");
                String direction = config.getString("Team " + identifier + ".Bed.Direction");
                Team team = new Team(color, size, x, y, z, yaw, bedX, bedY, bedZ, direction);
                teams.add(team);
            } catch (NullPointerException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Something went wrong while loading the Teams from the teams.yml file, " +
                        "a value is missing.");
                break;
            } catch (IllegalArgumentException e) {
                plugin.getServer().getConsoleSender().sendMessage(plugin.PluginTag + ChatColor.RED + "CONFIG ERROR: Something went wrong while loading the Teams from the teams.yml file, "
                        + "an illegal argument was found. Arguments of Team: " + color.toString() + " are faulty.");
                break;
            }
        }
        return teams;
    }

    public static void setTeamSize(String name, int size) throws NoSuchElementException {
        try {
            MyColor color = MyColor.valueOf(name); //check if team exists
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("The Team with the Name: " + name + " doesn't exist.");
        }
        config.set("Team " + name + ".Size", size);
        save();
    }

    public static void setTeamSpawn(String name, Location location) throws NoSuchElementException {
        try {
            MyColor color = MyColor.valueOf(name); //check if team exists
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("The Team with the Name: " + name + " doesn't exist.");
        }
        config.set("Team " + name + ".Spawn.X", (int) Math.round(location.getX()));
        config.set("Team " + name + ".Spawn.Y", (int) Math.round(location.getY()));
        config.set("Team " + name + ".Spawn.Z", (int) Math.round(location.getZ()));
        config.set("Team " + name + ".Spawn.Yaw", location.getYaw());
        save();
    }

    public static void setTeamBed(String name, Location location) throws NoSuchElementException, IllegalArgumentException {
        try {
            MyColor color = MyColor.valueOf(name); //check if team exists
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("The Team with the Name: " + name + " doesn't exist.");
        }
        double yaw = location.getYaw();
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
        } else {
            throw new IllegalArgumentException("Couldn't calculate the direction of the Bed by Player's Yaw Value.");
        }
        config.set("Team " + name + ".Bed.X", (int) Math.round(location.getX()));
        config.set("Team " + name + ".Bed.Y", (int) Math.round(location.getY()));
        config.set("Team " + name + ".Bed.Z", (int) Math.round(location.getZ()));
        save();
    }
    
}
