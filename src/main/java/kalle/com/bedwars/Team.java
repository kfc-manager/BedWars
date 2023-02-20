package kalle.com.bedwars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Player> players = new ArrayList<>();
    private String name;
    private ChatColor color;
    private int size;
    private int[] playerSpawn;
    private int[] bedLocation;
    private BlockFace bedDirection;
    private Material bedMaterial;

    public Team(String color, int size, int[] playerSpawn, int[] bedLocation, String bedDirection)
            throws IllegalArgumentException, NullPointerException {
        if (size < 1 || playerSpawn.length != 4 || bedLocation.length != 3) {
            throw new IllegalArgumentException();
        }
        this.name = color;
        this.color = ChatColor.valueOf(color);
        this.size = size;
        this.playerSpawn = playerSpawn;
        this.bedLocation = bedLocation;
        this.bedDirection = BlockFace.valueOf(bedDirection);
        this.bedMaterial = Material.valueOf(color + "_BED");
    }

    public void addPlayer(Player player) throws IndexOutOfBoundsException {
        if (size < players.size() + 1) {
            throw new IndexOutOfBoundsException();
        }
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<String> getPlayerList() {
        List<String> list = new ArrayList<>();
        for (Player player : players) {
            list.add(color + player.getDisplayName());
        }
        return list;
    }

    public int getSize() {
        return size;
    }

    public boolean contains(Player player) {
        return players.contains(player);
    }

}
