package kalle.com.bedwars;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class TeamBed {

    private Material material;

    private double location[];
    private BlockFace direction;

    public TeamBed(MyColor color, double x, double y, double z, String direction) throws IllegalArgumentException {
        this.material = Material.valueOf(color.toString() + "_BED");
        this.location = new double[]{x, y, z};
        this.direction = BlockFace.valueOf(direction);
    }

    public void spawn() {

    }

}
