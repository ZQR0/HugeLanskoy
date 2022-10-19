package me.zqr0.hugelanskoy.Commands;

import me.zqr0.hugelanskoy.Lanskoy.Manager;
import me.zqr0.hugelanskoy.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.*;

public class SpawnLanskoy implements CommandExecutor {

    private final Manager manager;

    public SpawnLanskoy(Plugin thisPlugin) {
        manager = new Manager(thisPlugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        boolean isAggressive = true;

        try {
            manager.spawnLanskoy(player.getLocation(), isAggressive);
            player.sendMessage(ChatColor.GREEN + "Lanskoy на свободе!");
            return true;
        } catch (NullPointerException ex) {
            player.sendMessage(ChatColor.RED + "Error in this command");
            ex.printStackTrace();
            return false;
        }
    }
}
