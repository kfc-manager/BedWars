package kalle.com.bedwars;

import kalle.com.bedwars.configurations.SettingsConfiguration;
import kalle.com.bedwars.configurations.TeamsConfiguration;
import kalle.com.bedwars.modes.Game;
import kalle.com.bedwars.modes.Lobby;
import kalle.com.bedwars.modes.Mode;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BedWars extends JavaPlugin {

    public static String PluginTag = ChatColor.DARK_PURPLE + "[BedWars] " + ChatColor.RESET;

    private static Game game;
    private static Lobby lobby;

    private static Mode mode;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setupConfigurations();
        List<Team> teams = TeamsConfiguration.getTeams();
        game = new Game(SettingsConfiguration.getGameWorld(), teams);
        lobby = new Lobby(SettingsConfiguration.getLobbySpawn(), SettingsConfiguration.getMinPlayers(), teams);
        registerEvents();
        registerCommands();
        if (SettingsConfiguration.isReady()) mode = lobby;
        getServer().getConsoleSender().sendMessage(PluginTag + ChatColor.GREEN + "Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(PluginTag + ChatColor.RED + "Plugin has been disabled.");
    }

    private void setupConfigurations() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        TeamsConfiguration teamsConfig = new TeamsConfiguration(this);
        teamsConfig.setup();
        SettingsConfiguration settingsConfig = new SettingsConfiguration(this);
        settingsConfig.setup();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(game,this);
        getServer().getPluginManager().registerEvents(lobby,this);
    }

    private void registerCommands() {

    }

    public static Mode getMode() {
        return mode;
    }

    public static Game getGame() {
        return game;
    }

    public static Lobby getLobby() {
        return lobby;
    }

}
