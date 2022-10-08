package me.zqr0.hugelanskoy.Commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetGamemode implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        try {
            if (args[0].equalsIgnoreCase("creative")) {
                player.setGameMode(GameMode.CREATIVE);
                return true;
            }
            else if (args[0].equalsIgnoreCase("survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                return true;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            player.sendMessage(ChatColor.RED + "Can't change your gamemode\nERROR");
            return false;
        }

        return false;
    }
}
