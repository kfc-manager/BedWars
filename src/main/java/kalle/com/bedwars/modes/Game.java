package kalle.com.bedwars.modes;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.Team;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class Game extends Mode implements Listener {

    private World world;
    private List<Team> teams;

    public Game(World world, List<Team> teams) {
        this.world = world;
        this.teams = teams;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (BedWars.getMode() instanceof Game) {
            Player player = (Player) event.getEntity();
            //TODO respawn player at his team spawn
        }
    }

}
