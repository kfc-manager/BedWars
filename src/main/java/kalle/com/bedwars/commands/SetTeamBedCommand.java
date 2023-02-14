package kalle.com.bedwars.commands;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.configurations.TeamsConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeamBedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(BedWars.PluginTag + ChatColor.RED + "You do not have the permission to execute this command!");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(BedWars.PluginTag +  ChatColor.RED + "The command requires an argument!");
            return false;
        }
        if (args.length > 1) {
            sender.sendMessage(BedWars.PluginTag +  ChatColor.RED + "The command accepts only one argument!");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(BedWars.PluginTag +  ChatColor.RED + "The command must be executed by an Player!");
            return true;
        }
        try {
            Player player = (Player) sender;
            Location location = player.getLocation();
            TeamsConfiguration.setTeamBed(args[0], location);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(BedWars.PluginTag + ChatColor.RED + e.getMessage());
            return true;
        }
        sender.sendMessage(BedWars.PluginTag + ChatColor.GREEN + "The Spawn of the Team Bed: "
                + ChatColor.GOLD + args[0] + ChatColor.GREEN + " has been set to " + ChatColor.GOLD
                + "your current Location" + ChatColor.GREEN + "!");
        return true;
    }

}
