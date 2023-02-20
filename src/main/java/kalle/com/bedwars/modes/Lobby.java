package kalle.com.bedwars.modes;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class Lobby extends Mode implements Listener {

    private Location spawn;
    private int minPlayers;
    private List<Team> teams;

    public Lobby(Location spawn, int minPlayers, List<Team> teams) {
        this.spawn = spawn;
        this.minPlayers = minPlayers;
        this.teams = teams;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (BedWars.getMode() instanceof Lobby) {
            Player player = event.getPlayer();
            int lobbySize = 0;
            for (Team team : teams) {
                lobbySize += team.getSize();
            }
            if (lobbySize < Bukkit.getOnlinePlayers().size()) {
                player.kickPlayer(ChatColor.RED + "The Lobby is full, try again later!");
                return;
            }
            player.teleport(spawn);
            if (minPlayers == Bukkit.getOnlinePlayers().size()) {
                //TODO start timer and prepare game
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (BedWars.getMode() instanceof Lobby) {
            Player player = event.getPlayer();
            if (minPlayers > Bukkit.getOnlinePlayers().size()) {
                //TODO cancel timer
            }
            for (Team team : teams) {
                if (team.contains(player)) {
                    team.removePlayer(player);
                }
            }
        }
    }

}
