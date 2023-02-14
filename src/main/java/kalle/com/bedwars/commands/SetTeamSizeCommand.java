package kalle.com.bedwars.commands;

import kalle.com.bedwars.BedWars;
import kalle.com.bedwars.configurations.TeamsConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.NoSuchElementException;

public class SetTeamSizeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(BedWars.PluginTag + ChatColor.RED + "You do not have the permission to execute this command!");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(BedWars.PluginTag +  ChatColor.RED + "The command requires two arguments!");
            return false;
        }
        if (args.length > 2) {
            sender.sendMessage(BedWars.PluginTag +  ChatColor.RED + "The command accepts only two arguments!");
            return false;
        }
        try {
            int size = Integer.valueOf(args[1]);
            TeamsConfiguration.setTeamSize(args[0], size);
        } catch (NoSuchElementException e) {
            sender.sendMessage(BedWars.PluginTag + ChatColor.RED + e.getMessage());
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage((BedWars.PluginTag + ChatColor.RED
                    + "Second provided argument must be an Integer bigger than 0!"));
            return false;
        }
        sender.sendMessage(BedWars.PluginTag + ChatColor.GREEN + "The Team: "
                + ChatColor.GOLD + args[0] + ChatColor.GREEN + " has now a Team Size of: "
                + ChatColor.GOLD + args[1] + ChatColor.GREEN + "!");
        return true;
    }

}
