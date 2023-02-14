package kalle.com.bedwars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Player> players = new ArrayList<>();
    private MyColor color;
    private String name;
    private double spawn[];
    private int size;

    private TeamBed bed;

    private boolean respawn = true;

    public Team(MyColor color, int size,
                double playerX, double playerY, double playerZ, double yaw,
                double bedX, double bedY, double bedZ, String bedDirection) throws IllegalArgumentException {
        this.color = color;
        this.name = buildName(color);
        this.size = size;
        this.spawn = new double[]{playerX, playerY, playerZ, yaw};
        this.bed = new TeamBed(color, bedX, bedY, bedZ, bedDirection);
    }

    private String buildName(MyColor color) {
        String colorString = color.toString();
        String name = "";
        for (int i = 0; i < colorString.length(); i++) {
            if (colorString.charAt(i) == 95) {
                name += " ";
            } else {
                name += colorString.charAt(i);
            }
        }
        return name;
    }

    public void addPlayer(Player player) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (players.size() == size) {
            throw new IndexOutOfBoundsException("Team is already full.");
        }
        if (players.contains(player)) {
            throw new IllegalArgumentException("Player already joined the Team.");
        }
        players.add(player);
    }

    public void removePlayer(Player player) throws IllegalArgumentException {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("Player isn't assigned to the Team.");
        }
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public double[] getSpawn() {
        return spawn;
    }

    public MyColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
